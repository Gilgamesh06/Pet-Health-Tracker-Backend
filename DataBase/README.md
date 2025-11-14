# Data Base

* Se realizo el diseño y implementacion de la base de datos para la aplicacion: `Pet Health Tracker`

    > **Nota:** Aunque se va usar ORM, primero modelamos y validamos la db.


    ## Modelo Relacional

    ![Modelo Relacional](/Diagram/DataBase/modelo-realcional-db.png)

    ## Despliegue

    * Para facilitar cambios y manejo entre los diferentes miembros de desarrollo del backend se utiliza docker compose

        ### Instrucciones 

        1. Ingresar a la ruta `Pet-Health-Tracker-Backend/DataBase`
        2. Ejecutar el comando:

            ```bash
                docker compose up --build
            ```
            > **Nota:** se esta usando docker compose V2 consulte la version del suyo.
            
        3. Conectarse a la db

            * **Puerto:** 5440
            * **Nombre db:** pet_shop_test
            * **Usuario:** test
            * **Contraseña:** 123456

