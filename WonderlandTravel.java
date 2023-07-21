import java.util.*;

class City {
    int id;
    List<City> neighbors;

    public City(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }
}

public class WonderlandTravel {
    static int calculateBitwiseXOR(int N, List<int[]> edges) {
        City[] cities = new City[N + 1];
        for (int i = 1; i <= N; i++) {
            cities[i] = new City(i);
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            cities[u].neighbors.add(cities[v]);
            cities[v].neighbors.add(cities[u]);
        }

        int[][] xorLengths = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = i + 1; j <= N; j++) {
                xorLengths[i][j] = xorLengths[j][i] = i + j; // Corrected this line
            }
        }

        return dfs(cities[1], null, 0, xorLengths);
    }

    static int dfs(City city, City parent, int xorValue, int[][] xorLengths) {
        int xorSum = 0;
        for (City neighbor : city.neighbors) {
            if (neighbor != parent) {
                int roadLength = xorValue ^ xorLengths[city.id][neighbor.id];
                xorSum ^= roadLength;
                xorSum ^= dfs(neighbor, city, roadLength, xorLengths);
            }
        }
        return xorSum;
    }

    public static void main(String[] args) {
        int N = 6;
        List<int[]> edges = new ArrayList<>();
        edges.add(new int[]{1, 2});
        edges.add(new int[]{2, 3});
        edges.add(new int[]{1, 4});
        edges.add(new int[]{3, 5});
        edges.add(new int[]{2, 6});

        int result = calculateBitwiseXOR(N, edges);
        System.out.println(result); // Output will be 5
    }
}