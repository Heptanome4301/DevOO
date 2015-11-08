package tsp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IteratorCroissant extends IteratorSeq {


	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus qui sont successeurs de sommetCrt dans le graphe g,
	 * dans l'odre du cout le plus petit au cout le plus grand 
	 * @param nonVus
	 * @param sommetCrt
	 * @param g
	 */
	public IteratorCroissant(Collection<Integer> nonVus, int sommetCrt, Graphe g){
		super(nonVus, sommetCrt, g);
		trierCondidatsSelonCourtArc(sommetCrt,g);	
	}

	private void trierCondidatsSelonCourtArc(Integer sommetCrt,Graphe g) {
		Integer couts[] = new Integer[this.candidats.length]; 
		Map<Integer,Integer> corrependanceSommetCout = new HashMap<>(); 
		int index = 0;
		for(Integer i : this.candidats){
			couts[index] = g.getCout(sommetCrt, i);
			corrependanceSommetCout.put(couts[index], i);
			index++;
		}
		
		couts = new Quicksort(couts).getSortedValues();
		
		index = 0;
		for(Integer i : couts){
			this.candidats[index++] = corrependanceSommetCout.get(i);	
		}
		
	}
	
	
}


class Quicksort  {
  private Integer[] numbers;
  private int number;
  
  public Integer[] getSortedValues(){
	  return numbers;
  }

  public Quicksort(Integer[] values) {
    // check for empty or null array
    if (values ==null || values.length==0){
      return;
    }
    this.numbers = values;
    number = values.length;
    quicksort(0, number - 1);
  }

  private void quicksort(int low, int high) {
    int i = low, j = high;
    // Get the pivot element from the middle of the list
    int pivot = numbers[low + (high-low)/2];

    // Divide into two lists
    while (i <= j) {
      // If the current value from the left list is smaller then the pivot
      // element then get the next element from the left list
      while (numbers[i] < pivot) {
        i++;
      }
      // If the current value from the right list is larger then the pivot
      // element then get the next element from the right list
      while (numbers[j] > pivot) {
        j--;
      }

      // If we have found a values in the left list which is larger then
      // the pivot element and if we have found a value in the right list
      // which is smaller then the pivot element then we exchange the
      // values.
      // As we are done we can increase i and j
      if (i <= j) {
        exchange(i, j);
        i++;
        j--;
      }
    }
    // Recursion
    if (low < j)
      quicksort(low, j);
    if (i < high)
      quicksort(i, high);
  }

  private void exchange(int i, int j) {
    int temp = numbers[i];
    numbers[i] = numbers[j];
    numbers[j] = temp;
  }
} 
