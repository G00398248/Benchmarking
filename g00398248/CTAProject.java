import java.util.ArrayList;
import java.util.Arrays;

public class CTAProject {
    public static void main(String[] args) {
        long startTime, endTime;
        ArrayList<Integer> sizeList = new ArrayList<>(Arrays.asList(100, 250, 500, 1000, 2000, 4000, 6000, 8000, 10000, 15000));

        System.out.println();

        ArrayList<String> algoList = new ArrayList<>(Arrays.asList("Size", "Bubble Sort", "Insertion Sort", "Merge Sort", "Counting Sort", "Quick Sort"));

        for (int i = 0; i < algoList.size(); i++){
            String current_algo = algoList.get(i);
            if (current_algo.equals("Size")) {
                System.out.print(current_algo + " \t \t \t |\t");
                for (int n : sizeList) {
                    int numDigits = String.valueOf(n).length();
                    if (numDigits < 4)
                        System.out.print(n + "\t \t \t");
                    else
                        System.out.print(n + "\t \t");
                }
            }

            else {
                System.out.println();
                if (current_algo.equals("Quick Sort") || current_algo.equals("Merge Sort"))
                    System.out.print(current_algo + " \t \t |\t");
                else
                    System.out.print(current_algo + " \t |\t");

                for (int n : sizeList) {

                    int[] arr = randomArray(n);
                    int[] sortedArray = arr;

                    startTime = beginTime();
                    if (current_algo.equals("Bubble Sort")) {
                        bubbleSort(arr);
                    } else if (current_algo.equals("Insertion Sort")) {
                        insertionSort(arr);;
                    } else if (current_algo.equals("Merge Sort")) {
                        mergeSort(sortedArray, 0, sortedArray.length - 1);
                    } else if (current_algo.equals("Counting Sort")) {
                        countingSort(arr);
                    } else if (current_algo.equals("Quick Sort")) {
                        quickSort(sortedArray, 0, sortedArray.length - 1);;
                    }
                    endTime = endTime();
                    double elapsedTimeBubbleinMS = elapsTimeinMS(endTime, startTime);

                    System.out.print(elapsedTimeBubbleinMS + "\t \t");
                }
            }
        }
    }

    static int[] randomArray ( int n){
        int[] array = new int[n];
        for (int i = 0; i < n ; i++) {
            array[i] = (int)(Math.random() * 20000) ;
        }
        return array ;
    }

    private static double elapsTimeinMS(long endTime, long startTime) {
        long timeElapsed = endTime - startTime;
        double elapsedMillis = timeElapsed / 1000000.0;
        return elapsedMillis;
    }

    static long beginTime(){
        long startTime = System.nanoTime();
        return startTime;
    }

    static long endTime(){
        long endTime = System.nanoTime();
        return endTime;
    }

    // An optimized version of Bubble Sort
    static int[] bubbleSort(int[] arr) {
        int n = arr.length;
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were swapped by inner loop, then break
            if (!swapped)
                break;
        }
        return arr;
    }

    /*Function to sort array using insertion sort*/
    static int[] insertionSort(int[] arr)
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        return arr;
    }

    static void mergeSort(int arr[], int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m =l+ (r-l)/2;

            // Sort first and second halves
            mergeSort(arr, l, m); // leftmost to middle
            mergeSort(arr, m + 1, r); // middle + 1 to rightmost

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    static void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    static int largest(int[] arr)
    {
        int i;
        // Initialize maximum element as first element of array
        int max = arr[0];
        // Traverse array elements from second position and compare every element with current max
        for (i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];
        return max;
    }

    static int[] countingSort(int[] input) {
        int k = largest(input);
        int[] c = countElements(input, k);

        int[] sorted = new int[input.length];

        // Once you have the element counts, place elements on their positions
        // and decrement current value in count array by 1
        for (int i = input.length - 1; i >= 0; i--) {
            int current = input[i];
            sorted[c[current] - 1] = current;
            c[current] -= 1;
        }

        return sorted;
    }

    static int[] countElements(int[] input, int k) {
        int[] count = new int[k + 1];
        // Fill count array with 0
        Arrays.fill(count, 0);

        // Count occurrence of each element in the input array and save in the count array
        for (int i : input) {
            count[i] += 1;
        }

        // Modify count array as addition of preceding counts
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // return the final count array
        return count;
    }

    // A utility function to swap two elements
    static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /* This function takes last element as pivot, places the pivot element at its correct position in sorted
    array, and places all smaller (smaller than pivot) to left of pivot and all greater elements to right of pivot */
    static int partition(int[] arr, int low, int high)
    {

        // pivot
        int pivot = arr[high];

        // Index of smaller element and indicates the right position of pivot found so far
        int i = (low - 1);

        for(int j = low; j <= high - 1; j++)
        {
            // If current element is smaller than the pivot
            if (arr[j] < pivot)
            {
                // Increment index of smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    /* The main function that implements QuickSort
            arr[] --> Array to be sorted,
            low --> Starting index,
            high --> Ending index
    */
    static void quickSort(int[] arr, int low, int high)
    {
        if (low < high)
        {

            // pi is partitioning index, arr[p] is now at right place
            int pi = partition(arr, low, high);

            // Separately sort elements before partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
}
