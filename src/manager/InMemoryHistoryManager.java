package manager;/*
 *author s.timofeev 08.04.2022
 */

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    protected CustomLinkedList history = new CustomLinkedList(); // двунаправленный список для хранения истории
    protected HashMap<Integer, Node> historyHashMap = new HashMap<>();

    /**
     * Добавляет задачу в историю
     *
     * @param task
     */
    @Override
    public void add(Task task) {
        history.linkLast(task);
    }

    /**
     * Удаляет задачу из просмотра
     *
     * @param id
     */
    @Override
    public void remove(int id) {
        Node node = historyHashMap.get(id);

        if (node != null) {
            history.removeNode(node);
            historyHashMap.remove(id);
        }
    }

    /**
     * Получает историю задач
     *
     * @return List
     */
    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }


    /**
     * CustomLinkedList
     */
    public class CustomLinkedList {
        private Node head;
        private Node tail;
        private int size = 0;

        public CustomLinkedList() {
        }

        /**
         * add a new node (data) at the end if head is not null
         *
         * @param data
         */
        public void linkLast(Task data) {

            if (data == null) {
                return;
            }
            size++;
            Node newNode;

            if (head == null) {
                newNode = new Node(data, null, null);
                head = newNode;
                tail = head;
            } else {
                newNode = new Node(data, null, tail);
                // в случае, если задача ранее была в списке, то её необходимо удалить
                // и добавить новую, в конец
                if (historyHashMap.containsKey(data.getId())) {
                    remove(data.getId());
                }
                // могли удалить задачу
                if (tail == null) {
                    linkLast(data);
                }
                tail.next = newNode;
                tail = newNode;
            }
            historyHashMap.put(data.getId(), newNode);
        }

        /**
         * размер списка
         *
         * @return
         */
        public int size() {
            return size;
        }

        public void removeNode(Node unlinkNode) {
            Node prev = unlinkNode.previous;
            Node next = unlinkNode.next;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                unlinkNode.previous = null;
            }
            if (next == null) {
                tail = prev;
            } else {
                next.previous = prev;
                unlinkNode.next = null;
            }
            unlinkNode.data = null;
            size--;
        }

        /**
         * return tasks
         */
        public ArrayList<Task> getTasks() {
            ArrayList<Task> arrayList = new ArrayList<>();
            Node currentNode = head;
            while (currentNode != null) {
                arrayList.add(currentNode.data);
                currentNode = currentNode.next;
            }
            return arrayList;
        }
    }
}
