
public class GridLinkedList {
  Grid head, tail, cycle;// head, tail, and the start of a cycle respectively
  Grid cursor;
  int size;
  //double positive_power_number;

  GridLinkedList() {
    tail = null;
    head = tail;
    cycle = null;
    size = 0;
  }

  void add(Grid e) {
    size++;
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
      curr.next = null;
      curr.prev = null;
      curr = null;
      curr = head;
    }
    tail = null;
    cycle = null;
    head = null;
    size = 0;
    System.gc();
  }

  void cursorToNext() {
    cursor = cursor.next;
  }

  void cursorToPrev() {
    cursor = cursor.prev;
  }

  int getSize() {
    return size;
  }

  void testprint() {
    Grid curr = head;
    while (curr != null) {
      System.out.print(curr.hashCode() + "->");
      curr = curr.next;
    }
  }
}