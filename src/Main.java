import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Выберите пункт меню");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Показать все задачи");
        System.out.println("3. Получить задачу по идентификатору");
        System.out.println("4. Изменить статус задачи");

        Scanner scanner = new Scanner(System.in);

        while ( true){
            final String command = scanner.nextLine();
            if (command.equals("1")){
                System.out.println("Укажите название задачи");
                String name=scanner.nextLine();
                System.out.println("Укажите описание задачи");
                String description=scanner.nextLine();

               // Task task=TaskManager.addNewTask(name,description);
             //   System.out.println("задача с id="+task.id+ "создана!");
            }else if (command.equals("2")){
                TaskManager.printAllTasks();
            }else if (command.equals("3")){

                System.out.println("Укажите ID задачи");
                int id=scanner.nextInt();
                Task task=TaskManager.getTaskByID(id);

            }

        }

    }
}
