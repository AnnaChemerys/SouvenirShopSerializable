Консольний застосунок з використанням 3-рівневої архітектури задля забезпечення принципу open-closed.

Задля більш зручного використання функцій додатково створено юзерів із правами користувача та адміна 
(користувач може лише переглядати сувеніри та виробників, а також проводити пошук за заданими параметрами,
а адміністратору доступні CRUD функції)

Для коректного запису/читання з/у файл/а використана серіалізація

Для обробки даних використовувалися Streams

Задля підвищення рівня абстракції використані абстрактні класи, дженеріки та інтерфейси



Щиро прошу вибачення за сдачу роботи із запізненням, хворіла і деякий час була непрацездатною

Намагалася реалізувати роботу з файлом з допомогою jackson-dataformat-csv або OpenCsv, для опрацювання зрозумілих 
для читання людині файлів та засвоєння нової для себе технології, однак виникла проблема із 
конвертацією з csv рядка знов у поле об'єкта, що саме являється об'єктом з кількома полями 

Наразі і далі розбираюся із підходящими для цього інструментами
