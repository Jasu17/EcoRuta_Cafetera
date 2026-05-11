# EcoRuta Cafetera

Aplicación móvil Android para la digitalización del censo de fincas cafeteras en zonas rurales del departamento de Santander, Colombia. Desarrollada como proyecto académico de Ingeniería de Software II.

## Descripción

EcoRuta Cafetera permite a técnicos de campo registrar y gestionar información de fincas cafeteras sin necesidad de conexión a internet. Los datos se almacenan localmente en el dispositivo y pueden consultarse en cualquier momento, resolviendo el problema de conectividad intermitente en zonas rurales de alta pendiente.

## Funcionalidades

- Autenticación de usuarios con persistencia de sesión
- Registro de fincas con datos del propietario, municipio, tipo de cultivo y hectáreas
- Captura de coordenadas GPS en tiempo real
- Listado y eliminación de fincas registradas
- Validación de campos obligatorios antes de guardar
- Almacenamiento offline completo mediante SQLite

## Stack Tecnológico

- **Lenguaje:** Kotlin
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Base de datos local:** Room / SQLite
- **Navegación:** Navigation Component
- **UI:** ViewBinding + Material Design
- **Ubicación:** FusedLocationProviderClient (Google Play Services)
- **Sesión:** SharedPreferences

## Requisitos

- Android 8.0 o superior (API 26+)
- Android Studio Hedgehog o superior
- Kotlin 1.9.x
- Gradle 8.4

## Instalación

1. Clona el repositorio
   git clone https://github.com/Jasu17/EcoRuta_Cafetera/tree/main

2. Abre el proyecto en Android Studio

3. Sincroniza las dependencias con Gradle (Sync Now)

4. Conecta un dispositivo Android o inicia un emulador

5. Ejecuta la aplicación desde Android Studio

## Credenciales de prueba

Usuario: tecnico
Contrasena: 1234

Usuario: admin
Contrasena: admin123

## Estructura del proyecto

```
com.ecoruta.cafetera/
├── data/
│   ├── local/
│   │   ├── dao/          -- Interfaces de acceso a Room
│   │   ├── entity/       -- Entidades de la base de datos
│   │   └── AppDatabase   -- Instancia singleton de Room
│   └── repository/       -- Capa de abstracción entre DAO y ViewModel
├── ui/
│   ├── auth/             -- Login y manejo de sesión
│   └── finca/            -- Lista, formulario y adaptador de fincas
└── MainActivity          -- Contenedor de navegación
```

## Contexto académico

Proyecto desarrollado en el marco del curso Ingeniería de Software II.  
Institucion: Universidad Industrial de Santander

## Trabajo futuro

- Sincronizacion con backend en la nube
- Modulo de reportes y estadisticas
- Gestion de usuarios desde la aplicacion
- Autenticacion robusta con Firebase Authentication
- Adjuntar fotografias por finca
- Visualizacion de fincas en mapa