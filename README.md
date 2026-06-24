# Sabority Android

App Android nativa de [Sabority](https://github.com/DraigKuro/sabority), pensada para que el cliente del restaurante haga su pedido directamente desde la mesa, sin esperar a un camarero.

Es el cliente final del ecosistema: consume la [`sabority-api`](https://github.com/DraigKuro/sabority-api) y su contraparte en la gestión del restaurante es el panel de administración ([`sabority-admin-web`](https://github.com/DraigKuro/sabority-admin-web)).

## Stack

- **Kotlin**
- **Jetpack Compose** para una interfaz declarativa y moderna
- **Retrofit** + **Coroutines** para la comunicación con la API
- **Kotlinx Serialization** para el parseo de JSON
- **Hilt** para inyección de dependencias

## Arquitectura

La app sigue los principios de **Clean Architecture** y **MVVM**, con varios ViewModels especializados (gestión de pedidos, estado de la mesa, información del restaurante) en lugar de un único contenedor de estado global.

- Al iniciar, la app hace una única llamada a la API para obtener toda la información necesaria: datos del restaurante, carta y estado de la mesa.
- Esos datos se almacenan en estado observable (`StateFlow`/`LiveData`) dentro de los ViewModels, y se distribuyen a las pantallas mediante inyección de dependencias —sin repetir peticiones a la API.
- Cualquier cambio (pedido actualizado, estado de mesa) se refleja automáticamente en la interfaz gracias a este patrón reactivo (MVVM + Repository).

La interfaz se construye con componentes Compose reutilizables, organizados en básicos (botones, tarjetas de producto) e intermedios (pantallas de menú, carrito, detalle de producto), navegando entre ellos con Navigation Compose.

> Esta arquitectura reemplaza al `TableContext` de React que se usaba en la primera versión web del proyecto ([`sabority-web-legacy`](https://github.com/DraigKuro/sabority-web-legacy)).

## Funcionalidades

- **Ver menú** — navegación por categorías, detalle de cada producto con descripción, precio e imagen.
- **Realizar pedido** — añadir productos al carrito y enviarlo a cocina/barra. *(De momento sin personalización de ingredientes o preparación: el producto se añade tal cual.)*
- **Llamar al camarero** — solicitar asistencia del personal desde la mesa.
- **Pedir la cuenta** — solicitar el cierre de cuenta y ver el resumen del consumo.

## Estado del proyecto

- ✅ Las 4 funcionalidades principales del cliente están implementadas y operativas.
- ⚠️ Personalización de productos (ingredientes, preparación) pendiente — queda como mejora futura.

## Contexto del proyecto

Este repositorio forma parte de [Sabority](https://github.com/DraigKuro/sabority), proyecto de fin de curso (2º DAM). El cliente del comensal empezó como una web ([`sabority-web-legacy`](https://github.com/DraigKuro/sabority-web-legacy)) y evolucionó a esta app Android nativa para ofrecer una experiencia más fluida y adaptada al móvil.
