from fastapi import FastAPI, UploadFile, File, HTTPException
from io import BytesIO
from PIL import Image, UnidentifiedImageError

import torch

from transformers import (
    AutoImageProcessor,
    AutoModelForImageClassification
)

app = FastAPI(
    title="NutriSnap AI Service",
    version="1.0.0"
)

# =====================================================
# MODELO FOOD-101
# =====================================================

MODELO_ID = "eslamxm/vit-base-food101"

procesador = AutoImageProcessor.from_pretrained(
    MODELO_ID
)

modelo = AutoModelForImageClassification.from_pretrained(
    MODELO_ID
)

modelo.eval()


@app.get("/health")
def health():
    return {
        "status": "ok",
        "service": "nutrisnap-ai"
    }


@app.post("/predict")
async def predict(
    imagen: UploadFile = File(...)
):
    tipos_permitidos = {
        "image/jpeg",
        "image/png"
    }

    if imagen.content_type not in tipos_permitidos:
        raise HTTPException(
            status_code=400,
            detail="Formato no permitido. Solo JPG, JPEG y PNG."
        )

    contenido = await imagen.read()

    if not contenido:
        raise HTTPException(
            status_code=400,
            detail="La imagen no puede estar vacía."
        )

    try:
        # Verificamos que realmente sea una imagen válida
        imagen_verificacion = Image.open(
            BytesIO(contenido)
        )

        imagen_verificacion.verify()

        # La abrimos nuevamente porque verify() invalida
        # el objeto anterior para procesamiento posterior
        imagen_pil = Image.open(
            BytesIO(contenido)
        ).convert("RGB")

    except UnidentifiedImageError:
        raise HTTPException(
            status_code=400,
            detail="El archivo enviado no contiene una imagen válida."
        )

    except Exception:
        raise HTTPException(
            status_code=400,
            detail="No fue posible procesar la imagen."
        )

    ancho, alto = imagen_pil.size

    # Preparar la imagen según los requisitos del modelo
    inputs = procesador(
        images=imagen_pil,
        return_tensors="pt"
    )

    # Inferencia
    with torch.no_grad():

        salida = modelo(
            **inputs
        )

        probabilidades = torch.nn.functional.softmax(
            salida.logits[0],
            dim=0
        )


    top_probabilidades, top_indices = torch.topk(
        probabilidades,
        5
    )

    predicciones = []

    for probabilidad, indice in zip(
            top_probabilidades,
            top_indices
    ):

        nombre = modelo.config.id2label[
            indice.item()
        ]

        confianza = probabilidad.item()

        predicciones.append(
            {
                "nombre": nombre,
                "confianza": round(
                    confianza,
                    4
                )
            }
        )

    return {
        "success": True,
        "nombreArchivo": imagen.filename,
        "tipoContenido": imagen.content_type,
        "tamanoBytes": len(contenido),
        "ancho": ancho,
        "alto": alto,
        "modoColor": imagen_pil.mode,
        "alimentosDetectados": predicciones
    }