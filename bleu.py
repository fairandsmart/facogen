import  nltk.translate.bleu_score as bleu


reference_translation=['The cat is on the mat.'.split(),
                       'There is a cat on the mat.'.split()
                      ]
candidate_translation_1='the the the mat on the the.'.split()
candidate_translation_2='The cat is on the mat.'.split()

print("BLEU Score: ",bleu.sentence_bleu(reference_translation, candidate_translation_1))
