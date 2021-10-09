# kurly

![image](https://user-images.githubusercontent.com/46153703/115948416-38baff80-a509-11eb-8b28-11650ff9542d.png)

## api

사용시 구글 로그인 필요함.  
기록 DB에 남음.

메인  
http://www.kulry.shop  

api docs  
http://www.kulry.shop/v2/api-docs  

swagger2  
http://www.kulry.shop/swagger-ui.html  

actuator  
http://www.kulry.shop/actuator  

hal browser  
http://www.kulry.shop/browser/index.html  

없는값 조회 시  
404  
![image](https://user-images.githubusercontent.com/46153703/115948557-a8c98580-a509-11eb-985e-d53c514a156e.png)  

@Valid 실패 시  
400  
![image](https://user-images.githubusercontent.com/46153703/115948602-f514c580-a509-11eb-866a-ca6bef91a586.png)  

ex)  

전체조회  
GET http://www.kulry.shop/api/v1/goods  
개별조회  
GET http://www.kulry.shop/api/v1/goods/{id}  
저장  
POST http://www.kulry.shop/api/v1/goods  
