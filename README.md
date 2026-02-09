# Cifrado y Descifrado Cesar y Vigenére
Con este programa usted podra cifrar y descifrar mensajes con ayuda de los metodos de cifrado Cesar y Vigenére

## Requisitos
* Java 25
* Terminal

## Guía de ejecución
Para utilizar esta herramienta de cifrado, solo necesitas el archivo ejecutable AlitasCifradas.jar. Sigue estos pasos para ponerlo en marcha:
1. Asegurese de tener instalado Java (version 25 o superior). Puedes verificarlo abriendo la terminal y escribiendo el comando
```
java --version
```
2. Ubica el archivo AlitasCifradas.jar en tu computadora. Abra la terminal y dirijase al directorio donde se encuentra este archivo, usando en windows:
```
cd C:\Users\...\Downloads> //caso que el archivo.jar se encuentre en la carpeta de descargas
```   
4. Para iniciar la aplicacion escriba el siguiente comando en la terminal y presione enter:
```
java -jar AlitasCifradas.jar
```
Debera de ver un menu listando los comandos disponibles:

<img width="562" height="207" alt="image" src="https://github.com/user-attachments/assets/7e0ae575-f956-4eb1-95d8-3a94f099aee0" />

**TIP** recuarda que esta aplicacion cuenta con una interfaz grafica que podra abrir usando ``` java -jar AlitasCifradas InterfazGrafica ```

## Comandos Disponibles

A continuación se detallan las funciones de cada comando disponible en **AlitasCifradas.jar** y el formato exacto que debes seguir en la terminal para ejecutarlos.

#### 1. Cifrado y Descifrado César

Utiliza el algoritmo de desplazamiento clásico. Requiere un número entero como llave.

* **Cifrar:** Transforma un mensaje legible en uno cifrado usando un desplazamiento numérico.
```bash
java -jar AlitasCifradas.jar cifradoCesar "Tu Mensaje" 5

```
_El desplazamiento no puede ser un multiplo de 26 porque de esta manera volverias al mensaje original_


* **Descifrar:** Recupera el mensaje original proporcionando el mensaje cifrado y la llave correcta.
```bash
java -jar AlitasCifradas.jar descifradoCesar "Yz Rjsxfoj" 5

```



#### 2. Cifrado y Descifrado Vigenère

Utiliza una palabra clave para realizar un cifrado polialfabético, lo que lo hace más seguro que el método César.

* **Cifrar:** Protege el mensaje aplicando una clave de texto (palabra).
```bash
java -jar AlitasCifradas.jar cifradoVigenere "Tu Mensaje" "CLAVE"

```


* **Descifrar:** Revierte el proceso de cifrado usando la palabra clave correspondiente.
```bash
java -jar AlitasCifradas.jar descifradoVigenere "Vf Xprufol" "CLAVE"

```



#### 3. Ataques de Fuerza Bruta

Estos comandos intentan adivinar el contenido del mensaje cuando **no conoces la llave**.

* **Fuerza Bruta César:** Prueba automáticamente todas las 25 combinaciones posibles de desplazamiento y las muestra en lista.
```bash
java -jar AlitasCifradas.jar fuerzaCesar "MensajeCifrado"

```


* **Fuerza Bruta Vigenère:** Intenta descifrar el mensaje comparándolo con una lista de palabras comunes (diccionario).
```bash
java -jar AlitasCifradas.jar fuerzaVigenere "MensajeCifrado"

```
**EL DICCIONARIO DEBERA ESTAR EN UNA CARPETA LLAMADA DATA DENTRO DEL MISMO DIRECTORIO**


#### 4. Interfaz Gráfica (GUI)

Si prefieres no usar la terminal para cada operación, puedes lanzar la aplicación visual.

* **Abrir Ventana:** Despliega una ventana amigable con botones y cuadros de texto para realizar todas las operaciones anteriores.
```bash
java -jar AlitasCifradas.jar interfazGrafica

```



---

> **Nota importante:** Siempre escribe el mensaje entre comillas (`" "`) si este contiene espacios, para que la terminal lo reconozca como un solo argumento.

