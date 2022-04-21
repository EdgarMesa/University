def kStream(lfsr):
    stream = []
    status = Initial_s(len(lfsr))
    initial = status

    i = 0
    while True:
        stream.append(stream_generator(lfsr, status))
        status = new_state(status)
        i += 1

        if status == initial or i > pow(2, len(lfsr)) - 1:

            break
    return stream

def stream_generator(lfsr, status):
    length = 1024
    key_stream = ""
    rks = ""
    index = len(status) - 1
    while index >= 0:
        rks += status[index]
        index -= 1

    for i in range(length):
        key_stream += rks[len(status) - 1]
        num = "2"
        j = len(lfsr) - 1
        while j >= 0:
            if lfsr[j] == 1:
                if num == "2":
                    num = rks[j]
                else:
                    if num == "0" and rks[j] == "0":
                        num = "0"
                    elif num == "1" and rks[j] == "1":
                        num = "0"
                    else:
                        num = "1"
            j -= 1
        tmp = ""
        tmp += num
        for ind in range(len(rks) - 1):
            tmp += rks[ind]
        rks = tmp

    return key_stream

def best_hits(positions):
    top = 0
    index = 0
    best = [None] * 20
    for i in range(len(positions)):
        if positions[i] > top:
            top = positions[i]
            index = i
    best[0] = index
    positions[index] = positions[index] * (-1)
    je = 1
    top2 = 0
    while(je < 20):
        top2 == 0
        for i in range(len(positions)):
            if positions[i] > top2 and positions[i] <= top:
                top2 = positions[i]
                index = i
        positions[index] = positions[index] * (-1)
        top = top2
        best[je] = index
        je += 1
    return best
def buildSol(bestlf, top1, top2, top3, keys1, keys2, keys3, i):
    if keys1[top1[0]][i] == "0":
        if keys2[top2[0]][i] == "0":
            bestlf += "0"
        else:
            if keys3[top3[0]][i] == "1":
                bestlf += "1"
            else:
                bestlf += "0"
    else:
        if keys2[top2[0]][i] == "0" and keys3[top3[0]][i] == "0":
            bestlf += "0"
        elif keys2[top2[0]][i] == "0" and keys3[top3[0]][i] == "1":
            bestlf += "1"
        elif keys2[top2[0]][i] == "1" and keys3[top3[0]][i] == "0":
            bestlf += "1"
        elif keys2[top2[0]][i] == "1" and keys3[top3[0]][i] == "1":
            bestlf += "1"
    return bestlf


def hit(stream, k):
    hits = 0
    for i in range(len(stream)):
        if ord(stream[i]) - 48 == k[i]:
            hits += 1

    return hits

def hits(streams, k):
    positions = []
    for stream in streams:
        positions.append(hit(stream, k))
    return positions


def new_state(status):
    new_one = ""
    iter = 0
    index = len(status) - 1

    while(index >= 0):
        if status[index] == "0":
            iter = index
            index = -1
        index -= 1
    for i in range(len(status)):

        if i < iter:
            new_one += status[i]
        elif i > iter:
            new_one += "0"
        else:
            new_one += "1"

    return new_one

def Initial_s(len):
    initial_status = ""
    for i in range(len-1):
        initial_status += "0"
    initial_status += "1"
    return initial_status



if __name__ == "__main__":
    lf1 = [0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1]
    lf2 = [0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1]
    lf3 = [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1]
    lf4 = [0, 1, 0, 1]
    key_stream = [0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,0,1,0,0,1,1,0,0,1,0,0,0,1,1,0,1,0,1,0,0,1,0,1,1,0,0,0,1,0,1,1,0,1,0,1,1,0,0,0,1,1,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,1,1,1,0,1,0,1,1,1,0,0,0,0,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,1,1,0,0,0,0,1,1,0,0,0,1,1,1,1,0,1,1,1,0,1,0,0,1,1,1,0,1,0,1,0,1,0,1,1,1,1,1,0,0,1,0,1,0,0,1,0,0,0,0,1,0,1,0,0,0,1,1,0,1,0,1,0,1,1,1,0,0,1,0,0,1,0,1,0,0,0,0,0,1,1,1,0,0,0,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,1,1,0,1,1,1,1,0,0,0,1,0,1,0,0,0,1,0,1,0,1,1,1,0,1,1,0,1,0,1,0,1,0,1,1,1,1,0,1,0,0,0,1,0,0,0,0,1,1,0,0,1,1,1,1,0,0,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1,0,0,0,0,0,1,0,1,0,1,1,0,1,0,0,0,1,0,1,0,0,1,1,0,1,1,0,1,1,0,0,0,0,0,1,1,1,1,0,0,0,1,1,1,0,0,1,1,0,0,1,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,0,0,0,1,1,0,1,1,1,1,0,1,0,1,1,1,1,0,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,1,1,0,1,0,1,0,0,1,1,0,1,1,0,0,0,1,0,0,0,1,0,1,1,1,1,0,1,1,1,0,0,0,1,1,1,1,0,1,0,0,1,1,1,0,0,0,0,0,0,0,1,0,1,1,0,0,0,1,0,1,0,1,0,0,1,0,0,0,0,0,0,1,0,1,0,1,0,1,0,0,1,1,0,1,0,1,0,1,0,0,0,0,1,0,1,0,0,0,0,1,1,0,1,0,1,0,0,0,1,1,1,0,1,0,0,1,0,1,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,0,0,0,0,0,0,0,1,0,0,1,1,0,0,1,0,0,1,0,1,0,0,0,1,0,1,0,0,1,1,0,0,1,0,1,0,0,0,1,0,1,1,1,0,0,1,1,0,0,0,1,0,0,0,1,1,0,0,0,1,1,1,0,1,0,1,1,1,0,0,0,0,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,0,1,1,1,0,0,1,1,0,1,0,1,1,1,0,0,0,0,0,1,1,0,0,1,1,1,0,1,0,1,0,0,1,0,1,1,0,1,1,0,0,0,1,1,1,1,0,0,1,1,1,0,0,0,1,0,0,1,0,1,1,0,1,0,0,0,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,0,0,0,1,1,1,0,1,0,1,0,0,0,1,0,1,1,0,1,1,1,0,1,1,1,0,1,1,0,0,1,0,1,1,1,1,0,0,1,1,0,1,1,0,1,0,0,1,0,0,1,1,1,1,0,0,1,1,0,0,0,0,1,0,1,0,0,1,1,0,0,1,0,1,1,0,1,0,1,0,1,0,1,1,1,0,1,0,0,0,0,1,1,1,1,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,1,1,1,0,1,1,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,1,1,0,1,1,1,1,0,1,0,0,1,1,0,0,1,0,1,0,1,1,0,0,1,0,1,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,0,1,0,0,1,0,0,0,1,1,1,0,0,0,1,0,0,0,1,0,1,1,0,0,1,1,0,0,0,0,0,0,0,1,1,1,0,0,1,1,0,0,0,1,0,0,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,1,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,0,1,1,1,0,1,0,0,0,0,0,1]
    bits = 1024
    keys1 = kStream(lf1)
    rank1 = hits(keys1, key_stream)
    top1 = best_hits(rank1)
    keys2 = kStream(lf2)
    rank2 = hits(keys2, key_stream)
    top2 = best_hits(rank2)
    keys3 = kStream(lf3)
    rank3 = hits(keys3, key_stream)
    top3 = best_hits(rank3)

    bestlf = ""

    for i in range(bits):
        bestlf = buildSol(bestlf, top1, top2, top3, keys1, keys2, keys3, i)

    best_hits_rate = hit(bestlf, key_stream)

    print("Best lfsr hits:", best_hits_rate)
    print("Best lfsr:", bestlf)
