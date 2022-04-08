# java-sprint2-hw
Second sprint homework

Техническое задание
Как человек обычно делает покупки? Если ему нужен не один продукт, а несколько, то очень вероятно, 
что сначала он составит список, чтобы ничего не забыть. Сделать это можно где угодно: на листе бумаги, 
в приложении для заметок или, например, в сообщении самому себе в мессенджере. А теперь представьте, 
что это список не продуктов, а полноценных дел. И не каких-нибудь простых вроде «помыть посуду» или «позвонить бабушке», 
а сложных — например, «организовать большой семейный праздник» или «купить квартиру». 
Каждая из таких задач может разбиваться на несколько этапов со своими нюансами и сроками. 
А если над их выполнением будет работать не один человек, а целая команда, то организация процесса станет ещё сложнее.
------------------------------------------------------------------------------------------------------------------------
Трекер задач
Как системы контроля версий помогают команде работать с общим кодом, 
так и трекеры задач позволяют эффективно организовать совместную работу над задачами. 
Вам предстоит написать бэкенд для такого трекера. В итоге должна получиться программа, 
отвечающая за формирование модели данных для этой страницы:

------------------------------------------------------------------------------------------------------------------------
NB! Пользователь не будет видеть консоль вашего приложения. 
Поэтому нужно сделать так, чтобы методы не просто печатали что-то в консоль, но и возвращали объекты нужных типов.
Вы можете добавить консольный вывод для самопроверки в класcе Main, но на работу методов он влиять не должен.


Типы задач
Простейшим кирпичиком такой системы является задача (англ. task). У задачи есть следующие свойства:
Название, кратко описывающее суть задачи (например, «Переезд»).
Описание, в котором раскрываются детали.
Уникальный идентификационный номер задачи, по которому её можно будет найти.
Статус, отображающий её прогресс. Мы будем выделять следующие этапы жизни задачи:
* NEW — задача только создана, но к её выполнению ещё не приступили.
* IN_PROGRESS — над задачей ведётся работа.
* DONE — задача выполнена.
Иногда для выполнения какой-нибудь масштабной задачи её лучше разбить на подзадачи (англ. subtask). 
Большую задачу, которая делится на подзадачи, мы будем называть эпиком (англ. epic).
Таким образом, в нашей системе задачи могут быть трёх типов: обычные задачи, эпики и подзадачи. 
Для них должны выполняться следующие условия:
Для каждой подзадачи известно, в рамках какого эпика она выполняется.
Каждый эпик знает, какие подзадачи в него входят.
Завершение всех подзадач эпика считается завершением эпика.

---------------------------------------------------------------------------------------------------------
updates #2
Класс TaskManager должен стать интерфейсом. В нём нужно собрать список методов, 
которые должны быть у любого объекта-менеджера. Вспомогательные методы, если вы их создавали, 
переносить в интерфейс не нужно.
Созданный ранее класс менеджера нужно переименовать в InMemoryTaskManager. Именно то, что менеджер хранит 
всю информацию в оперативной памяти, и есть его главное свойство, позволяющее эффективно управлять задачами.
Внутри класса должна остаться реализация методов. При этом важно не забыть имплементировать TaskManager — в Java класс 
должен явно заявить, что он подходит под требования интерфейса.

История просмотров задач
Добавьте в программу новую функциональность — нужно, чтобы трекер отображал последние просмотренные пользователем 
задачи. Для этого реализуйте метод getHistory() — он должен возвращать последние 10 просмотренных задач. 
Просмотром будем считаться вызов у менеджера методов получения задачи по идентификатору — 
getTask(), getSubtask() и getEpic(). 
От повторных просмотров избавляться не нужно.
