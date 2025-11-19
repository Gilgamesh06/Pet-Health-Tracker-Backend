# Despliegue

* La app esta empaquetada en contenedores docker y se genero un archivo `docker-compose.yml` para facilitar el despliegue.

    * **Comandos para desplegar**

        ```bash
            cd Pet-Health-Tracker-Backend/Container
            docker compose up --build
        ```
    * **Comando para listar contenedores activos**

        ```bash
            docker ps 
        ```
    * **Comando para eliminar contenedores levantados**

        ```bash
            docker compose down 
        ```

    * **Comando para eliminar contenedores y volumenes**

        ```bash
            docker compose down --volumes
      ```
