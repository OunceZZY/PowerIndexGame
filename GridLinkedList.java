
public class GridLinkedList {
  Grid head, tail, cycle;// head, tail, and the start of a cycle respectively
  Grid cursor;

  GridLinkedList() {
    tail = null;
    head = tail;
    cycle = null;
  }

  void add(Grid e) {
    if (head == null) {
      head = e;
      tail = head;
      cursor = head;
      return;
    }
    tail.next = e;
    e.prev = tail;
    tail = tail.next;
  }

  void clear() {
    Grid curr = head;
    while (curr != null) {
      System.out.println("gll clean");
      head = head.next;
      curr = null;
      curr = head;
    }
  }

  void cursorToNext(){
    cursor = cursor.next;
  }
}