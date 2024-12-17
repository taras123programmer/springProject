import mysql.connector
import random
db = mysql.connector.connect(
host = "localhost",
user = "root",
password = "123456",
database = "Schedule"
)
##Встановлюємо з'єднання з сервером
cursor = db.cursor()
objects = [
           "Програмування Інтернет",
           "Конструювання програмного забезпечення",
           "Програмування мовою Java",
           "Бази даних",
           "Програмування вбудованих систем",
           "Програмування на Python",
           "Програмування на PHP",
           "Операційні системи",
           "Англійська мова",
           "Веб-програмування",
           "Математичний аналіз",
           "Алгоритми та структури даних",
           "Людинно-машинна взаємодія",
           "Компютерна графіка",
           "Якість програмного забезпечення",
           "Економіка програмного забезпечення"
           ]

# for i in range(100):
#     object = objects[random.randint(0, len(objects)-1)]
#     teacher_id = random.randint(1, 6)
#     corps = random.randint(1, 2)
#     cabinet = random.randint(1, 200)
#     type = random.randint(1, 4)
#     cursor.execute("INSERT INTO couple(object, teacher_id, corps, cabinet, type) VALUES (\"%s\", %d, %d, %d, %d)" % (
#         object, teacher_id, corps,cabinet, type))
# #Заповнюємо таблицю couple

#db.commit()

for i in range(1, 6):
    for j in range(1, 5):
        cursor.execute("INSERT INTO `group`(faculty, specialty, number, course, department) VALUES (\"%s\", \"%s\", %d, %d, \"%s\")" %
        ("Факультет математики та інформатики", "ІПЗ", i*10+j, i, "Інформаційних технологій"))
#Заповнюємо таблицю group   

db.commit()

# # for month in range(1, 12):
# for day in range(1, 31):
#     for group in range(1, 21):
#         try:
#             cursor.execute("INSERT INTO Schedule(group_id, date) VALUES (%d, \"2024-12-%d\")" % (group, day))
#         except: pass
# #Заповнюємо таблицю schedule

# db.commit()

# cursor.execute("SELECT count(*) FROM schedule")
# n = cursor.fetchone()[0]
# print(n)
# for i in range(6581, n+1):
#     for j in range(1,7):
#         if(random.randint(1,4) != 1): 
#             cursor.execute("INSERT INTO couple_schedule(couple_id,schedule_id, number) VALUES (%d, %d, %d)" % (random.randint(2, 100), i, j))
# #Заповнюємо таблицю couple_schedule

# db.commit()
#Зберігаємо зміни

# cursor.close()
# db.close()