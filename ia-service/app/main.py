from fastapi import FastAPI, UploadFile, File, HTTPException

app = FastAPI(
    title="NutriSnap AI Service",
    version="1.0.0"
)


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

    return {
        "success": True,
        "nombreArchivo": imagen.filename,
        "tipoContenido": imagen.content_type,
        "tamanoBytes": len(contenido),
        "alimentosDetectados": []
    }