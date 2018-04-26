
public class GridLinkedList {
  Grid head, tail, cycle;// head, tail, and the start of a cycle respectively
  DiffusionStrategy ds;

  GridLinkedList() {
    head = tail;
    tail = null;
    cycle = null;
  }

  void add(Grid e) {
    if (head == null) {
      head = e;
      tail = head;
    }
    tail.next = e;
    e.prev = tail;
    tail = tail.next;
  }

  
}