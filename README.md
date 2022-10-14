# Contenedores: Práctica de Acceso a Datos DAM curso 2022/23
![image](https://user-images.githubusercontent.com/90746957/195692649-c0c74924-c602-4533-888a-ef5b087f1269.png)
### Autores: Roberto Blázquez y Francisco Toribio
La práctica pretende evaluar el uso y manejo de ficheros mediante programación así como el filtrado y mapeo de la información en ellos contenida.
Para su realización hemos elegido el lenguaje de programación Kotlin y el IDE IntelliJ. Realizamos el control de versiones con Github.
### Enlace al vídeo en YouTube donde se explican las partes más relevantes de la práctica y se muestra su ejecución con todas las opciones de llamada, con el distrito de Chamberí y otra con otro distrito:
## Enunciado
Nos ha contratado el Ayuntamiento de Madrid para que hagamos un estudio del análisis de
limpieza y gestión de basuras que se hace en la ciudad.
Para ello nos han dado unos ficheros con la información deseada que debemos procesar.
Nuestro programa se debe llamar de la siguiente manera: java -jar basuras.jar opcion
[parámetros_opcion]
Si la opción es parser directorio_origen directorio_destino: debe tomar los ficheros csv
del directorio origen y transformalos en JSON y XML en el directorio destino. En dicho
directorio destino deberán estar las tres versiones: CSV, JSON y XML.
Si la opción es resumen directorio_origen directorio_destino: debe tomar la información
de los contenedores y de la recogida, independientemente de la extensión que tenga (si no
corresponde a la extensión o al formato deberá indicar error) y deberá procesarla
generando en directorio_destino un resumen.html, aplicándoles los estilos que creas
oportunos, con la siguiente información:
- Título: Resumen de recogidas de basura y reciclaje de Madrid
- Fecha de generación: Fecha y hora en formato español.
- Autores: Nombre y apellidos de los dos autores.
- Número de contenedores de cada tipo que hay en cada distrito.
- Media de contenedores de cada tipo que hay en cada distrito.
- Gráfico con el total de contenedores por distrito.
- Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por
distrito.
- Gráfico de media de toneladas mensuales de recogida de basura por distrito.
- Máximo, mínimo, media y desviación de toneladas anuales de recogidas por cada tipo
de basura agrupadas por distrito.
- Suma de todo lo recogido en un año por distrito.
- Por cada distrito obtener para cada tipo de residuo la cantidad recogida.
- Tiempo de generación del mismo en milisegundos.
Si la opción es resumen distrito directorio_origen directorio_destino: debe tomar la
información de los contenedores y de la recogida, independientemente de la extensión que
tenga (si no corresponde a la extensión o al formato deberá indicar error) y deberá
procesarla generando en directorio_destino un resumen_distrito.html (solo si el distrito
existe, si no deberá mostrar error), aplicándoles los estilos que creas oportunos, con la
siguiente información:
- Titulo: Resumen de recogidas de basura y reciclaje de “Distrito”
- Fecha de generación: Fecha y hora en formato español.
- Autores: Nombre y apellidos de los dos autores.
- Número de contenedores de cada tipo que hay en este distrito.
- Total de toneladas recogidas en ese distrito por residuo.
- Gráfico con el total de toneladas por residuo en ese distrito.
- Máximo, mínimo , media y desviación por mes por residuo en dicho distrito.
- Gráfica del máximo, mínimo y media por meses en dicho distrito.
- Tiempo de generación del mismo en milisegundos.
Por cada ejecución debemos guardar un fichero bitacora.xml donde tengamos en este
XML un listado de las ejecuciones con la siguiente información:
- ID de la ejecución en base a uuid
- Instante: Instante de la ejecución en formato ISO 8601
- Tipo de opción elegida (parser, resumen global, resumen ciudad).
- Éxito: si tuvo éxito o no su procesamiento.
- Tiempo de ejecución: tiempo de ejecución si tuvo éxito en milisegundos.
Tienes los PDFs necesarios donde te explica cómo y de qué manera la información está
estructurada. Debes leerlos detalladamente para saber cómo y dónde está la información
relevante y cómo está expresada. Antes de programar a lo loco, te recomiendo estudiar y
analizar esta información para pensar cómo y de qué manera puedes hacer lo que se te
pide de la forma más efectiva y eficiente. No hay una solución buena única, pero sí muchas
malas. Tú decides.
