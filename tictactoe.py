from random import randrange
def display_board(board):
    for i in range (3):
        print("+-------+-------+-------+\n|       |       |       |\n|  " + str(board[i][0]) +"    |   " + str(board[i][1]) + "   |   " + str(board[i][2]) +"   |\n|       |       |       |\n+-------+-------+-------+")
   
    return


def enter_move(board):
    frees = make_list_of_free_fields(board)
    try:
        move = int(input("please enter a move"))
        if move in frees:
            for i in range(3):
                for j in range(3):
                    if (move==board[i][j]):
                        board[i][j] = 'O'
        else:
            enter_move(board, frees)
    except:
        enter_move(board,frees)


def make_list_of_free_fields(board):
    li = []
    for i in range (3):
        for j in range(3):
            if board[i][j] not in ["X", "O"]:
                li.append(board[i][j])
    return li


def victory_for(board, sign):
    for i in range (3):
        if ((board[i][0] == sign) and (board[i][1] == sign) and (board[i][2] == sign)):
            print("Player" , sign , "has won!")
            return True
        elif ((board[0][i] == sign) and (board[1][i] == sign) and (board[2][i] == sign)):
            print("Player" , sign , "has won!")
            return True
    if ((board[0][0] == sign) and (board[1][1] == sign) and (board[2][2] == sign)):
        print("Player" , sign , "has won!")
        return True
    if ((board[0][2] == sign) and (board[1][1] == sign) and (board[2][0] == sign)):
        print("Player" , sign , "has won!")
        return True

    return False


def draw_move(board):
    r = 0
    frees = make_list_of_free_fields(board)
    while (r not in (frees)):
        r = randrange(1,10)
    if r in frees:
        for i in range(3):
                for j in range(3):
                    if (r==board[i][j]):
                        board[i][j] = 'X'
                        frees = make_list_of_free_fields(board)        
    return



board = [[1,2,3],[4,"X",6],[7,8,9]]
frees = make_list_of_free_fields(board)
counter = 0
display_board(board)
print(frees)
while((len(frees) > 0)):
    counter += 1
    print("TURN", counter)
    enter_move(board)
    display_board(board)
    if (victory_for(board, "O") == True):
        break;
    counter += 1
    print("TURN", counter)
    draw_move(board)
    display_board(board)
    if (victory_for(board, "X") == True):
        break;
    frees = make_list_of_free_fields(board)
if (len(frees) == 0):
    print("tie game")