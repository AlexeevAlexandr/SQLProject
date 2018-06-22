import java.util.Arrays;

public class DataSet {
    static class Data{
        String name;
        Object value;

        String getName() {
            return name;
        }

        Object getValue() {
            return value;
        }

        Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
    private Data [] data = new Data[100]; //TODO remove magic number 100
    private int index = 0;

    void put(String name, Object value) {
        data[index ++] = new Data (name, value);
    }

    String [] getNames(){
        String [] result = new String[index];
        for (int i = 0; i < index; i++) {
            result [i] = data[i].getName();
        }
        return result;
    }
    Object [] getValues(){
        Object [] result = new Object[index];
        for (int i = 0; i < index; i++) {
            result [i] = data[i].getValue();
        }
        return result;
    }

    @Override
    public String toString() {
        return "DataSet{\n" +
                "names: " + Arrays.toString(getNames()) + "\n" +
                "values: " + Arrays.toString(getValues()) + "\n" +
                "---------------\n";
    }
}
