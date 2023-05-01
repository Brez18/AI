import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Tic_Tac_Toe {

    static ArrayList<ArrayList<String>> board = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> magic_square = new ArrayList<>();

    static void createMagicSquare(){
        magic_square.get(0).add(8);
        magic_square.get(0).add(3);
        magic_square.get(0).add(4);

        magic_square.get(1).add(1);
        magic_square.get(1).add(5);
        magic_square.get(1).add(9);

        magic_square.get(2).add(6);
        magic_square.get(2).add(7);
        magic_square.get(2).add(2);
    }

    static void showBoard()
    {
        for (ArrayList<String> row: board) {
            for (String move: row)
                System.out.print(move);
            System.out.println();
        }
    }

    static String hasGameEnded()
    {
        int count_X = 0,count_O = 0;

        // checking rows
        for (ArrayList<String> row: board) {
            for (String move: row) {
                if (Objects.equals(move, " X "))
                    count_X++;
                else if (Objects.equals(move, " O "))
                    count_O++;
            }
            if (count_X == 3 || count_O == 3)
                break;
            else
            {
                count_X = 0;
                count_O = 0;
            }

        }
        if (count_X == 3 )
            return "X";
        else if(count_O == 3)
            return "O";


        //checking columns

        for(int k=0;k<3;k++)
        {
            for(int j = 0; j<3; j++)
            {
                if(Objects.equals(board.get(j).get(k), " X "))
                    count_X++;
                else if (Objects.equals(board.get(j).get(k), " O "))
                    count_O++;
            }
            if (count_X == 3 || count_O == 3)
                break;
            else
            {
                count_X = 0;
                count_O = 0;
            }
        }
        if (count_X == 3 )
            return "X";
        else if(count_O == 3)
            return "O";

        //checking diagonals

        if( (Objects.equals(board.get(0).get(0), " X ") && Objects.equals(board.get(1).get(1), " X ") && Objects.equals(board.get(2).get(2), " X "))) // diagonal one
            return "X";
        else if( (Objects.equals(board.get(0).get(0), " O ") && Objects.equals(board.get(1).get(1), " O ") && Objects.equals(board.get(2).get(2), " O "))) // diagonal one
            return "O";

        if( (Objects.equals(board.get(0).get(2), " X ") && Objects.equals(board.get(1).get(1), " X ") && Objects.equals(board.get(2).get(0), " X "))) // diagonal 2
            return "X";
        else if( (Objects.equals(board.get(0).get(2), " O ") && Objects.equals(board.get(1).get(1), " O ") && Objects.equals(board.get(2).get(0), " O "))) // diagonal 2
            return "O";

        return "N";
    }

    static boolean makeMoveAt(int row, int col, String move)
    {
        if(Objects.equals(board.get(row).get(col), " - ")) {
            board.get(row).set(col,move);
            return true;
        }

        return false;

    }

    static ArrayList<Integer> findIndex(int d)
    {
        ArrayList<Integer> arr = new ArrayList<>();
        for (ArrayList<Integer> row : magic_square) {
            if(row.contains(d))
            {
                arr.add(magic_square.indexOf(row));
                arr.add(row.indexOf(d));
            }
        }
        return arr;
    }

    static void AImakemove()
    {
        ArrayList<ArrayList<Integer>> blocks = new ArrayList<>();

        for(int i = 0 ; i <= 2; i++)
        {
            for (int j = 0; j <= 2 ; j++) {
                if (Objects.equals(board.get(i).get(j), " X ")) {
                    blocks.add(new ArrayList<>());
                    blocks.get(blocks.size() - 1).add(i);
                    blocks.get(blocks.size() - 1).add(j);
                }
            }
        }
        System.out.println(blocks);
        int d;
        ArrayList<Integer> arr = null;
        boolean flag = false;
        for(int i = 0; i< Objects.requireNonNull(blocks).size(); i++)
        {
            for(int j = i+1;  j< Objects.requireNonNull(blocks).size(); j++)
            {
                System.out.println(magic_square.get(blocks.get(i).get(0)).get(blocks.get(i).get(1)));
                System.out.println(magic_square.get(blocks.get(j).get(0)).get(blocks.get(j).get(1)));
                d = 15 - ( magic_square.get(blocks.get(i).get(0)).get(blocks.get(i).get(1)) + magic_square.get(blocks.get(j).get(0)).get(blocks.get(j).get(1)) ) ;
                if( d > 0 && d < 9 )
                {
                    arr = findIndex(d);
                    flag = makeMoveAt(arr.get(0),arr.get(1)," O ");
                    if (flag)
                        break;
                }
            }
            if (flag)
                break;
        }

        if (!flag)
        {
            for (ArrayList<String> row: board) {
                for (String move: row) {
                    if (Objects.equals(move, " - ")) {
                        board.get(board.indexOf(row)).set(row.indexOf(move), " O ");
                        flag = true;
                        break;
                    }
                }
                if (flag){
                  break;
                }
            }
        }
    }

    public static void main(String[] args) {

        // initializing  board
        for(int i = 0 ; i <= 2;i++)
        {
            board.add(new ArrayList<>());
            magic_square.add(new ArrayList<>());
            for (int j = 0; j <= 2 ;j++)
                board.get(i).add(" - ");
        }
        createMagicSquare();

        Scanner sc = new Scanner(System.in);
        int row,col;
        boolean flag = false;

        while(hasGameEnded().equals("N"))
        {
            while(!flag) {
                System.out.println("Where do you want to play X: ");
                System.out.print("row-> ");
                row = sc.nextInt();
                System.out.print("col-> ");
                col = sc.nextInt();
                System.out.println();

                flag = makeMoveAt(row, col, " X ");
                if (!flag)
                    System.out.println("Move already made at that row and column");
            }
            flag = false;
            showBoard();
            AImakemove();
            System.out.println();
            showBoard();
        }
    }
}
