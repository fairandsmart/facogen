import  nltk.translate.bleu_score as bleu
import sys

#reference_translation=['The cat is on the mat.'.split(),
#                       'There is a cat on the mat.'.split()
#                      ]
#candidate_translation_1='the the the mat on the the.'.split()

#candidate_translation_2='The cat is on the mat.'.split()

reference_translation = []
candidate_translation = sys.argv[1].split()

for i in range(2,len(sys.argv)):
    reference_translation.append(sys.argv[i].split())
    #print(sys.argv[i])

bleuScore = bleu.sentence_bleu(reference_translation, candidate_translation)

print(bleuScore)#, "score type ", type(bleuScore)) "BLEU Score: ",
#print("len ",len(sys.argv),"sys1 ",sys.argv[1])

