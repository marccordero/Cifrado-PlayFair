package main;

import java.util.*;

public class CifradoPlayFair {
    static int SIZE = 30;

    static void toLowerCase(char plain[], int ps) {
        int i;
        for (i = 0; i < ps; i++) {
            if (plain[i] > 64 && plain[i] < 91)
                plain[i] += 32;
        }
    }

    static int removeSpaces(char[] plain, int ps) {
        int i, count = 0;
        for (i = 0; i < ps; i++)
            if (plain[i] != '\u0000')
                plain[count++] = plain[i];

        return count;
    }

    static void generateKeyTable(char key[], int ks, char keyT[][]) {
        int i, j, k;
        int dicty[] = new int[26];
        for (i = 0; i < ks; i++) {
            if (key[i] != 'j')
                dicty[key[i] - 97] = 2;
        }
        dicty['j' - 97] = 1;
        i = 0;
        j = 0;
        for (k = 0; k < ks; k++) {
            if (dicty[key[k] - 97] == 2) {
                dicty[key[k] - 97] -= 1;
                keyT[i][j] = key[k];
                j++;
                if (j == 5) {
                    i++;
                    j = 0;
                }
            }
        }
        for (k = 0; k < 26; k++) {
            if (dicty[k] == 0) {
                keyT[i][j] = (char) (k + 97);
                j++;
                if (j == 5) {
                    i++;
                    j = 0;
                }
            }
        }
    }

    static void search(char keyT[][], char a, char b, int arr[]) {
        int i, j;
        if (a == 'j')
            a = 'i';
        else if (b == 'j')
            b = 'i';
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (keyT[i][j] == a) {
                    arr[0] = i;
                    arr[1] = j;
                } else if (keyT[i][j] == b) {
                    arr[2] = i;
                    arr[3] = j;
                }
            }
        }
    }

    static int mod5(int a) {
        return (a % 5);
    }

    static int prepare(char str[], int ptrs) {
        if (ptrs % 2 != 0) {
            str[ptrs++] = 'z';
            str[ptrs] = '\0';
        }
        return ptrs;
    }

    static void encrypt(char str[], char keyT[][], int ps) {
        int i;
        int[] a = new int[4];
        for (i = 0; i < ps; i += 2) {
            search(keyT, str[i], str[i + 1], a);
            if (a[0] == a[2]) {
                str[i] = keyT[a[0]][mod5(a[1] + 1)];
                str[i + 1] = keyT[a[0]][mod5(a[3] + 1)];
            } else if (a[1] == a[3]) {
                str[i] = keyT[mod5(a[0] + 1)][a[1]];
                str[i + 1] = keyT[mod5(a[2] + 1)][a[1]];
            } else {
                str[i] = keyT[a[0]][a[3]];
                str[i + 1] = keyT[a[2]][a[1]];
            }
        }
    }

    static void encryptByPlayfairCipher(char str[], char key[]) {
        int ps;
        int ks;
        char[][] keyT = new char[5][5];

        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce el texto clave: ");
        String keyString = scanner.nextLine();
        ks = keyString.length();
        strcpy(key, keyString);

        System.out.print("Introduce el texto plano: ");
        String plainString = scanner.nextLine();
        strcpy(str, plainString);

        ks = removeSpaces(key, ks);
        toLowerCase(key, ks);
        ps = str.length;
        toLowerCase(str, ps);
        ps = removeSpaces(str, ps);
        ps = prepare(str, ps);
        generateKeyTable(key, ks, keyT);
        encrypt(str, keyT, ps);

        scanner.close();
    }

    static void strcpy(char[] arr, String s) {
        for (int i = 0; i < s.length(); i++) {
            arr[i] = s.charAt(i);
        }
    }

    public static void main(String[] args) {
        char str[] = new char[SIZE];
        char key[] = new char[SIZE];

        encryptByPlayfairCipher(str, key);

        System.out.println("Cipher text: " + String.valueOf(str));
    }
}
