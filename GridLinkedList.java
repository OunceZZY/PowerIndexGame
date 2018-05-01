
public class GridLinkedList {
  Grid head, tail, cycle;// head, tail, and the start of a cycle respectively
  Grid cursor;
  int size;
  int precycleSize;
  int cycleSize;
  //double positive_power_number;

  GridLinkedList() {
    tail = null;
    head = tail;
    cycle = null;
    size = 0;
    precycleSize = 0;
    cycleSize = 0;
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

  void cleanAfter(Grid h) {
    Grid curr = h;
    while (curr != null) {
      h = h.next;
      curr.next = null;
      curr.prev = null;
      curr = null;
      curr = h;
    }
    System.gc();
  }

  void clear() {
    tail = null;
    cycle = null;
    cleanAfter(this.head);
    head = null;
    size = 0;
    System.gc();
  }

  void cursorToNext() {
    cursor = cursor.next;
  }

  void cursorToPrev() {
    cursor = cursor.prev;
    /*Grid curr = head;
    while (curr != cursor) {
      System.out.print(curr.hashCode() + "->");
      curr = curr.next;
    }
    System.out.println("");*/
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
    System.out.println("");
  }
}