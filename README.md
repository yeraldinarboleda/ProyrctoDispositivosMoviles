# ProyectoDispositivosMoviles

## 1. Descripción General del Proyecto

Este proyecto consiste en un sistema cliente/servidor completo compuesto por:

✔ Aplicación móvil Android (Java):

Registro

Inicio de sesión

Gestión completa de tareas: crear, listar, editar y eliminar

Comunicación con el backend por medio de Retrofit y JSON

Backend REST (Node.js + Express):

API REST con autenticación mediante token

CRUD completo de tareas

Módulo de sesiones

Validación de datos

 Base de datos MySQL:

Tabla usuarios

Tabla tareas

Tabla sesiones (manejo de tokens)

La conexión se realiza mediante una URL pública temporal creada con ngrok o localtunnel.

 ## 2. Arquitectura del Sistema
Android (Java + Retrofit)
        ↓ REST JSON
Node.js (Express + JWT-like tokens)
        ↓ SQL
MySQL

## 3. Base de Datos
3.1 Diagrama Entidad–Relación
usuarios (1) ---- (N) sesiones
usuarios (1) ---- (N) tareas

3.2 Tablas
Tabla: usuarios
Campo	Tipo	Descripción
id	INT PK AI	Identificador
nombre	VARCHAR(80)	Nombre completo
correo	VARCHAR(120) UNIQUE	Email
password	VARCHAR(255)	Hash BCRYPT
Tabla: sesiones
Campo	Tipo	Descripción
id	INT PK AI	Identificador
usuario_id	INT FK	Relación con usuario
token	VARCHAR(255)	Token para la app
creado_en	DATETIME	Fecha de creación
Tabla: tareas
Campo	Tipo	Descripción
id	INT PK AI	Identificador
titulo	VARCHAR(120)	Título
descripcion	TEXT	Información detallada
fecha_limite	DATE	Fecha límite
usuario_id	INT FK	Dueño de la tarea

## 4. Backend – API REST (Node.js + Express)
4.1 Tecnologías
Componente	Tecnología
Lenguaje	Node.js
Framework	Express
Seguridad	bcrypt, crypto
Base de datos	mysql2
Cors	Habilitado
Tokens	Guardados en tabla sesiones

##. Endpoints implementados
Autenticación
POST /api/register

Registra un usuario nuevo.

POST /api/login

Devuelve un token guardado en sesiones.

Tareas
GET /api/items

Lista todas las tareas del usuario propietario del token.

POST /api/items

Crear una tarea.

PUT /api/items/:id

Editar una tarea existente.

DELETE /api/items/:id

Eliminar una tarea.

## 6. Frontend Android (Java)
Tecnologías

Android Studio

Java



Pantallas
1.Registro

Formulario → POST /api/register

2.Login

Formulario → POST /api/login
Guarda el token y el id del usuario.

3.Lista de tareas

Obtiene tareas con:
GET /api/items

RecyclerView + Adapter

Cada item incluye:

Botón Editar

Botón Eliminar

4.Crear / Editar tarea

Formulario que envía:

POST /api/items para crear

PUT /api/items/:id para editar

## 7. Conexión con el Backend

Ejecutar:

ngrok http 3000


URL resultante:

https://xxxxx.ngrok.io/api/


Esta URL se asigna en Retrofit:

public static final String BASE_URL = "https://xxxxx.ngrok.io/api/";

## 8. Instalación
 Backend

Clonar repositorio

Instalar dependencias:

npm install


Configurar db.js con credenciales MySQL

Crear la base de datos e importar las tablas

Iniciar servidor:

npm start

 App Android

Abrir en Android Studio

Editar la URL del backend

Ejecutar en emulador o dispositivo físico

## 9. Pruebas – POSTMAN
Ejemplo LOGIN
POST /api/login
{
  "correo": "prueba@mail.com",
  "password": "1234"
}


Respuesta:

{
  "token": "asdj12893bd9182",
  "usuario_id": 5
}

## 10. Video demostrativo

https://drive.google.com/file/d/1xr-kaWHIlmPyKs3mUaoiMV9LXFcvZuwa/view?usp=sharing


