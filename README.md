# GasyAndroid
Esta es una aplicación para llevar control de los gastos y consumos de gasolina, de un vehiculo o moto.

Se esta usando  para inyeccion de dependencias [RoboGuice](https://github.com/roboguice/roboguice)

Se esta usando una arquitectura N capas. 

Se tienen dos modulos por el momento, el modulo APP el cual refiere a la aplicación para smarthphones,
y un modulo llamado CORE el cual refiere a los componentes principales de la aplicación, como modelo, DTO, DAO. 
Esto con el fin de poder añadir modulos para dispositivos WEAR luego y reutilizar componentes. 

Para el manejo de versiones, se tendra la rama principal MASTER, y una rama DEVELOP para el desarrollo, en caso de hacer 
contribuciones, por favor cree una rama con el nombre FEATURE/#NNN donde NNN representa un numero  consecutivo de tres cifras;
Al hacer pull request por favor hacerlo a la rama DEVELOP desde una rama FEATURE.
