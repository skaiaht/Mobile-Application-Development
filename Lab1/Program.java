public class Program {
    public static int binarySearchRecursiveInternal(int[] array, int target, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;

        if (array[mid] == target) {
            return mid;
        } else if (array[mid] > target) {
            return binarySearchRecursiveInternal(array, target, low, mid - 1);
        } else {
            return binarySearchRecursiveInternal(array, target, mid + 1, high);
        }
    }

    public static int binarySearchRecursive(int[] array, int target) {
        return binarySearchRecursiveInternal(array, target, 0, array.length - 1);
    }

    public static int binarySearchIterative(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] sortedArray = {78, 92, 113, 256, 349, 512};
        int target = 92;
        int index = binarySearchRecursive(sortedArray, target);

        if (index != -1) {
            System.out.println("[rec] found at index: " + index);
        } else {
            System.out.println("[rec] not found");
        }

        target = 72;
        index = binarySearchRecursive(sortedArray, target);

        if (index != -1) {
            System.out.println("[rec] found at index: " + index);
        } else {
            System.out.println("[rec] not found");
        }

        sortedArray = new int[]{78, 92, 113, 256, 349, 512};
        target = 349;
        index = binarySearchIterative(sortedArray, target);

        if (index != -1) {
            System.out.println("[iter] found at index: " + index);
        } else {
            System.out.println("[iter] found");
        }

        target = 379;
        index = binarySearchIterative(sortedArray, target);

        if (index != -1) {
            System.out.println("[iter] found at index: " + index);
        } else {
            System.out.println("[iter] not found");
        }
    }
}