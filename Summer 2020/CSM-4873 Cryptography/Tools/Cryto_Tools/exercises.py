import numpy as np
import sys
from stegano import lsb
import re

np.set_printoptions(threshold=sys.maxsize)



cipher = np.array((88,123,63,3,10,23,107,67,66,28,112,71,125,80,73,57,37,56,97,2,109,16,45,54,48,24,83,100,93,32,25,124,10,53,91,79,78,120,29,48,88,16,6,40,78,100,26,27,40,68,105,70,92,31,4,44,23,122,59,60,22,92,117,20,9,108,119,117,40,109,19,122,37,125,51,103,9,87,66,113,89,64,38,20,56,40,23,74,124,101,30,112,104,48,120,108,30,15,37,6,54,118,34,73,96,65,34,24,24,35,83,87,66,2,62,50,110,23,81,49,98,51,118,125,14,127,11,13,24,5,66,44,59,10,94,69,78,94,3,51,21,11,111,57,2,90,119,76,65,34,43,0,61,100,20,7,81,103,27,47,2,116,12,91,41,16,80,126,112,75,109,84,48,70,40,29,90,9,115,8,105,16,36,16,113,76,43,21,93,77,125,24,115,43,69,17,19,27,31,78,110,107,0,58,64,97,32,49,91,51,99,89,53,123,107,38,87,5,42,96,73,75,14,119,64,106,76,59,86,112,64,104,64,96,9,93,93,27,109,116,114,71,59,89,95,5,119,28,31,61,13,68,52,105,97,112,50,83,8,81,2,1,26,92,46,98,92,29,88,40,103,106,113,59,32,80,76,117,114,59,73,85,48,58,87,83,116,70,13,35,21,26,51,16,92,123,11,26,55,97,26,42,116,101,85,18,3,72,60,30,37,52,58,16,55,14,114,5,22,22,99,97,99,12,74,38,79,7,5,18,5,44,83,110,116,120,75,90,78,93,47,61,40,120,22,23,82,102,74,85,113,102,44,77,5,22,27,80,108,64,51,84,92,23,115,98,122,84,82,105,59,33,31,39,86,80,65,79,49,89,68,120,16,80,12,2,83,125,34,68,13,95,71,98,5,79,16,29,50,22,117,63,62,71,92,118,40,70,35,102,25,83,55,53,119,11,2,2,54,63,8,12,33,53,111,59,81,31,17,33,16,125,62,99,61,108,56,35,21,95,46,121,100,33,95,89,109,103,63,40,69,12,92,111,120,39,57,104,12,89,89,3,43,59,2,4,119,28,33,82,60,77,52,112,13,45,104,29,111,85,107,60,23,27,6,86,89,19,55,20,92,80,21,101,103,28,62,65,119,85,1,51,106,86,105,36,21,91,65,72,111,29,127,115,54,82,46,97,53,102,84,34,23,84,116,69,1,57,24,31,79,66,14,51,43,126,9,113,83,56,88,25,67,107,119,57,74,42,3,10,72,20,32,9,119,53,87,66,27,101,32,34,61,3,54,74,99,3,38,75,38,111,123,54,70,97,15,115,75,40,50,42,79,14,9,90,33,85,88,74,41,113,42,32,100,28,17,72,109,40,60,95,74,43,51,45,34,117,50,60,25,111,53,57,59,15,25,14,112,118,63,31,98,97,61,16,103,120,123,45,61,7,110,39,68,77,56,97,33,8,46,8,11,126,20,0,6,5,101,39,85,59,0,103,20,27,108,103,52,6,6,120,2,8,71,81,55,111,45,93,112,48,34,67,99,114,90,68,42,92,91,51,85,12,27,94,98,61,18,78,34,47,88,24,97,123,27,79,21,77,86,39,75,30,18,92,71,87,88,57,57,107,35,0,24,19,122,28,0,124,0,116,22,127,118,0,79,41,116,114,29,58,61,122,3,109,110,70,31,83,14,8,18,24,46,87,42,39,126,10,124,104,19,10,82,114,93,46,106,75,64,64,19,61,45,121,60,110,47,86,107,40,83,25,123,34,25,26,64,102,42,97,118,26,36,26,118,99,116,103,98,94,74,81,117,48,11,81,105,67,96,25,96,110,109,10,84,81,121,14,55,115,50,3,108,100,120,111,21,105,119,55,59,36,98,35,116,106,2,29,121,38,108,42,71,114,55,25,0,125,117,118,127,10,26,0,85,59,18,96,97,105,77,100,8,117,121,92,18,13,33,93,29,22,52,19,57,70,53,113,57,23,25,30,117,117,93,96,124,34,86,76,113,66,7,40,57,32,104,23,120,8,107,47,49,49,49,78,98,101,92,121,23,124,119,49,41,52,65,41,93,95,78,107,59,34,33,0,93,58,7,33,89,83,104,72,32,111,89,33,22,37,106,122,13,10,39,112,33,111,37,31,1,79,123,30,8,103,64,57,0,62,48,62,121,14,59,98,70,81,82,67,32,62,94,28,102,64,75,82,46,104,4,114,3,109,108,21,23,63,22,68,69,85,66,30,101,59,24,22,62,70,120,118,55,76,98,96,70,96,79,56,19,1,39,29,31,71,112,38,52,14,53,48,86,40,47,32,46,109,5,120,20,106,55,112,106,29,70,25,126,96,89,45,83,64))
key = np.array((106,91,79,17,27,33,6,85,34,40,8,102,9,48,104,75,44,24,126,25,125,40,72,68,16,33,106,112,117,128,37,20,37,21,107,93,95,8,56,62,100,23,102,57,104,68,42,54,54,94,4,99,104,127,17,71,52,8,86,89,29,60,8,31,22,120,87,128,53,8,115,15,64,4,64,71,18,110,78,9,57,91,64,46,83,69,35,97,6,0,50,119,72,60,16,7,126,28,68,25,81,86,48,100,111,76,57,38,51,54,110,105,78,15,30,81,123,119,2,93,18,19,11,24,21,12,93,109,84,28,91,67,71,41,114,37,88,121,17,64,44,28,1,70,98,107,17,44,82,52,70,83,73,123,39,34,49,119,58,75,98,17,35,107,65,43,94,11,80,99,12,94,75,38,70,56,117,27,83,19,118,43,64,112,15,83,11,39,124,89,20,41,5,56,37,43,36,41,127,107,124,2,12,81,93,128,52,17,119,74,115,109,70,14,10,50,110,34,10,127,91,103,110,10,87,126,99,71,117,126,71,72,93,113,28,112,104,45,4,17,17,83,82,106,113,89,87,58,42,73,109,80,76,4,65,128,64,100,38,101,29,20,39,60,63,124,60,42,115,69,114,120,12,27,53,107,83,85,14,82,86,97,62,81,117,94,128,93,30,53,117,50,82,26,119,91,30,57,83,124,122,54,12,0,104,114,26,91,76,44,68,81,70,39,84,45,6,101,48,39,113,65,118,29,87,50,47,38,21,34,25,67,112,13,0,15,92,108,91,47,15,119,63,6,35,35,50,2,101,98,14,116,67,107,32,50,123,110,115,32,109,98,123,41,8,66,45,107,102,125,86,47,127,62,104,48,16,23,121,39,24,88,28,104,39,98,100,15,61,23,25,118,90,125,101,95,47,57,18,31,20,76,30,85,119,73,63,88,45,1,43,95,82,81,87,34,20,98,5,6,87,85,115,21,32,77,49,85,28,53,23,93,12,49,17,76,7,106,100,38,2,89,15,115,12,43,77,23,94,52,96,30,104,79,71,123,6,55,92,45,34,82,114,27,11,35,4,124,56,95,73,88,79,12,109,57,121,125,40,108,127,90,50,41,18,54,3,46,69,38,123,99,117,127,120,42,30,77,15,112,97,91,27,4,73,53,37,118,79,103,123,52,16,5,22,93,59,124,81,70,110,51,37,52,0,93,28,25,51,49,108,80,21,67,55,21,26,3,51,73,114,121,98,75,8,75,101,125,15,33,91,47,128,25,22,81,41,34,87,0,46,57,71,30,82,42,125,17,55,94,6,7,18,67,38,11,42,1,93,71,69,10,108,37,25,114,60,99,44,42,53,9,69,128,113,35,30,84,8,59,28,104,105,56,19,76,2,18,73,76,49,10,67,25,71,39,56,26,80,19,80,50,128,120,79,43,3,88,26,13,80,34,123,52,99,102,83,65,42,31,58,32,107,29,116,21,33,12,69,53,112,90,28,4,34,34,124,115,69,64,26,23,27,13,113,104,80,10,63,120,126,75,2,76,122,126,114,36,63,119,98,19,105,39,45,119,110,85,114,91,65,66,115,120,0,8,123,91,45,104,54,51,102,38,30,60,91,114,106,82,69,3,111,77,98,93,66,100,77,127,9,11,34,23,86,31,47,62,15,121,125,72,88,25,31,77,8,84,48,102,110,39,114,40,57,105,71,63,25,38,92,116,50,26,109,68,61,101,124,43,87,76,32,29,62,7,83,7,70,104,10,60,51,51,12,48,44,110,32,16,69,111,8,57,55,115,3,67,1,110,111,106,101,100,85,57,42,94,73,77,107,45,114,9,123,41,114,101,20,110,85,14,79,34,119,113,19,79,33,1,18,23,80,63,105,3,128,9,18,56,89,47,11,55,39,17,23,45,17,14,5,74,95,19,50,23,114,83,114,105,0,118,45,114,35,128,6,119,46,109,42,117,56,40,79,29,84,84,21,125,81,50,121,50,6,6,109,64,15,65,114,103,81,97,103,66,68,52,124,119,21,15,8,67,76,3,17,127,116,0,47,5,46,15,18,17,52,65,92,9,122,126,97,6,27,54,64,12,120,72,91,1,98,107,3,90,0,37,106,46,49,53,2,90,64,41,50,14,50,125,62,49,28,47,9,57,37,120,89,75,23,68,75,90,89,26,83,1,82,49,105,93,0,74,118,55,70,85,102,89,14,116,35,2,30,77,117,48,37,90,118,80,86,97,97,50,121,66,120,36,93,88,20,7,74,32,66,108,94,123,97,24,48,15,46,45,43,102,2,69,72,21,66,71,99,8,56,49,57,1,33,88,50,5,23,7,125,45,87,38,11,119,119,65,110,18))

plain_values = (cipher - key) % 128

print("".join(chr(x) for x in plain_values))


print("--------------------------------------------------")
cipher2 = np.array((217,248,136,105,13,114,115,215,161,144,226,37,46,146,169,108,112,157,113,240,23,251,191,152,134,129,48,75,109,170,213,79,10,19,73,33,178,53,162,175,225,18,238,85,370,123,169,150,161,218,214,91,161,88,149,221,164,224,136,42,243,156,193,100,246,107,85,199,227,175,152,121,149,61,166,157,38,122,19,252,101,181,186,202,165,124,45,84,112,240,238,70,18,2,167,163,243,374,107,56,25,20,116,173,71,35,54,5,42,252,85,23,125,32,93,238,233,7,129,201,69,20,39,6,4,51,22,44,235,145,49,250,100,103,186,200,189,71,247,180,100,82,19,116,8,182,21,105,149,98,164,164,156,46,167,113,288,71,79,235,103,229,159,124,178,224,185,73,14,114,51,62,237,99,252,13,211,173,107,13,241,170,178,228,33,101,113,141,18,114,91,91,162,98,67,103,59,61,82,130,100,174,179,232,253,177,222,189,254,124,5,52,212,166,123,141,94,8,114,178,255,99,17,206,152,33,179,24,165,65,119,232,129,199,136,96,82,17,240,137,173,56,60,168,230,64,133,190,68,166,40,4,201,136,124,243,181,210,66,5,107,105,101,153,1,136,184,101,66,177,214,92,254,174,211,193,182,24,147,57,123,60,121,14,174,191,35,181,18,130,183,101,243,80,28,145,117,25,79,140,56,116,51,174,195,165,29,82,255,113,165,193,243,168,24,182,127,40,132,99,93,202,139,31,150,217,200,136,241,113,185,135,68,173,44,7,127,120,230,118,182,218,149,231,120,208,93,33,205,224,237,50,210,167,136,90,210,51,74,81,173,5,18,236,102,54,57,40,246,255,75,20,97,108,12,12,75,222,56,11,206,106,159,49,189,10,249,130,235,78,30,12,175,227,121,23,201,218,0,87,148,151,97,104,126,161,130,288,96,158,188,252,370,146,104,79,25,187,1,128,158,254,68,18,121,215,191,209,204,27,180,146,202,123,83,37,227,176,198,177,136,187,157,59,98,225,140,163,71,42,71,186,254,153,164,167,26,16,114,42,125,111,222,177,164,45,254,202,191,94,125,93,158,42,129,251,52,125,109,233,59,97,202,211,50,125,155,123,5,237,60,172,124,236,77,102,17,181,123,184,204,78,107,82,22,27,19,108,13,225,40,40,205,69,102,100,80,41,152,15,186,246,34,169,34,35,47,206,153,115,10,9,240,77,149,25,105,199,212,212,85,158,87,32,195,230,71,45,124,249,205,46,101,86,147,129,105,41,246,17,64,64,156,209,50,23,216,176,68,131,196,47,23,144,196,44,190,50,74,189,219,108,168,197,240,10,228,220,143,107,81,63,226,46,67,249,134,160,234,108,187,247,38,49,248,170,26,83,98,91,181,157,76,255,21,23,243,228,21,93,199,224,166,22,225,208,163,244,211,166,61,213,10,212,21,205,205,126,193,221,238,155,82,243,13,63,20,121,37,9,45,195,44,143,82,204,42,149,32,237,249,4,139,113,110,96,13,137,108,150,25,174,157,73,79,195,17,38,20,28,35,212,79,106,203,127,169,39,117,215,72,17,32,10,206,212,152,185,101,134,164,75,241,51,180,172,225,2,0,96,69,88,111,5,22,130,207,97,183,19,191,156,189,194,134,239,71,166,1,193,214,2,100,91,16,9,79,113,38,58,131,6,150,150,0,249,127,159,44,124,165,212,174,22,148,200,209,84,45,185,185,24,242,119,71,204,191,56,170,109,1,226,124,181,176,60,118,54,125,146,25,112,65,24,228,144,21,116,166,153,194,15,6,223,33,89,3,118,193,205,88,63,137,222,111,129,104,227,144,185,64,62,54,80,0,156,72,104,31,239,88,219,192,11,111,255,28,60,252,171,196,179,107,231,210,101,236,158,170,203,183,150,10,54,115,177,24,172,239,85,196,58,141,182,31,211,120,171,126,244,80,16,125,82,240,18,57,223,27,205,242,167,82,35,145,201,186,2,59,151,121,53,62,108,99,187,157,96,65,216,217,89,150,134,189,41,243,174,83,124,161,211,182,166,45,195,52,9,181,70,128,154,59,80,213,236,197,5,39,145,67,238,52,50,129,41,231,193,79,224,240,131,100,12,158,68,176,112,244,234,237,353,6,116,115,125,116,143,199,225,89,221,152,42,141,74,171,108,100,57,168,188,252,214,63,4,95,112,201,211,133,42,123,185,24,177,246,71,16,50,228,10,168,23,210,138,30,51,36,168,95,155,57,119,253,132,85,151,188,218,61,225,57,92,240,156,77,198,120,181,60,149,171,104,175,218,127,10,205,153))
key2 = np.array((249,153,228,26,98,82,17,178,196,254,194,85,92,253,223,9,30,189,5,152,118,143,159,249,232,248,16,40,4,218,189,42,120,51,62,72,198,93,130,219,137,119,206,37,256,20,217,243,211,174,175,123,206,62,181,173,193,146,238,79,144,232,225,23,147,8,39,162,128,214,184,20,224,78,210,189,83,9,118,220,14,208,195,185,133,11,68,32,24,208,139,32,116,103,196,215,154,256,14,84,96,52,0,197,34,3,69,100,71,153,117,101,24,81,40,135,155,98,236,172,43,96,84,38,101,64,54,99,191,193,17,145,1,30,201,230,157,3,158,211,13,38,114,24,40,192,112,27,230,11,203,202,239,14,200,23,256,40,33,142,74,145,246,17,215,192,201,40,106,82,80,87,157,11,153,127,160,141,3,108,135,207,146,134,68,0,31,173,103,1,62,63,130,0,58,71,85,92,38,235,11,192,192,200,155,222,172,157,157,14,108,64,189,197,26,225,126,108,27,194,147,12,124,175,236,72,208,56,196,47,19,200,236,174,228,9,38,112,130,240,141,91,83,197,139,53,235,215,39,199,92,109,166,230,80,211,215,167,54,37,31,1,0,185,113,250,215,7,46,212,187,47,222,193,181,225,197,125,240,76,9,89,89,101,203,198,3,209,123,241,195,23,154,50,105,229,28,118,33,172,80,21,69,203,227,200,124,54,154,81,209,169,150,197,56,223,18,88,246,2,62,190,226,124,247,181,232,238,158,3,153,234,43,222,88,39,30,8,150,26,223,185,244,147,17,191,51,82,227,192,171,91,160,212,252,122,182,86,57,50,223,108,112,137,2,22,91,81,214,185,57,117,15,7,44,65,34,178,84,110,188,74,246,95,157,59,193,186,217,98,62,120,199,134,89,120,167,191,45,35,253,250,4,72,14,192,230,256,23,255,207,220,256,247,69,38,119,205,100,238,234,155,32,50,16,185,159,224,245,42,131,241,184,2,35,81,140,246,170,208,239,192,220,87,14,193,231,198,62,89,103,219,140,252,132,206,116,102,29,70,8,27,177,195,221,13,137,163,203,54,93,5,209,120,178,194,3,69,93,219,14,28,164,243,3,68,170,76,43,205,115,194,92,166,56,10,104,149,73,138,224,110,90,107,39,34,63,76,88,207,123,6,237,21,7,16,53,71,236,47,139,218,17,152,18,15,24,255,160,83,125,104,131,109,252,106,26,178,177,176,117,234,56,0,132,143,43,79,25,139,185,14,51,51,225,239,8,68,214,119,47,50,188,165,90,114,248,232,11,209,228,64,103,245,182,77,202,91,37,211,251,25,219,160,148,42,130,179,253,75,37,87,135,14,38,151,229,210,147,28,207,158,73,95,216,197,124,115,3,123,218,243,41,210,97,126,158,129,53,45,166,132,136,54,165,181,209,157,165,195,89,245,108,166,122,160,237,22,168,174,206,205,55,129,99,94,121,89,70,96,93,171,73,253,126,236,94,253,69,205,138,125,248,5,11,13,45,254,13,229,57,207,189,42,38,179,121,67,102,60,87,188,46,30,235,28,198,74,23,190,38,116,68,42,175,244,245,220,22,245,197,44,148,19,195,197,149,106,32,1,101,51,10,124,54,240,170,0,211,51,217,238,210,175,166,142,103,214,116,175,181,106,1,63,48,125,46,1,67,20,163,79,248,182,105,141,12,191,67,14,204,179,199,120,245,164,241,50,66,203,212,52,210,33,34,190,209,89,199,74,114,194,15,204,195,72,19,91,93,229,120,3,97,110,145,252,123,17,212,248,160,99,99,255,67,60,96,23,180,190,61,31,253,182,10,161,3,134,233,153,52,95,70,53,32,235,41,27,63,142,120,183,175,100,31,211,60,75,148,194,167,219,75,144,179,22,204,236,207,190,196,243,110,22,4,217,125,194,138,35,161,72,173,194,119,182,88,199,17,155,32,48,16,51,148,119,25,190,59,171,135,203,62,3,242,176,217,110,94,185,89,122,80,9,78,207,244,13,36,248,172,42,243,166,222,72,158,203,115,16,192,167,211,212,1,227,67,97,208,40,160,208,84,35,176,156,173,37,106,240,54,140,91,64,230,71,130,225,61,133,147,236,3,98,247,62,213,20,212,158,133,256,114,84,26,27,84,251,175,132,121,182,253,83,173,62,202,28,1,25,223,217,142,179,31,112,48,4,168,191,233,83,91,203,121,223,146,40,125,30,196,126,192,114,188,170,125,65,93,216,43,250,87,22,145,253,38,254,207,250,74,142,76,48,148,188,47,163,88,220,81,229,196,27,220,179,29,102,168,183))
plain_values2 = np.bitwise_xor(cipher2, key2)

print("".join(chr(x) for x in plain_values2))

print("--------------------------------------------------")
   

file = open("words.txt", 'r')
words = file.read().split(sep="\n")
words = [word.upper().strip() for word in words]

clean_words = []

for wor in words:
    wor = re.sub(r'[^A-Z]', "", wor)
    if len(wor) > 0:
        clean_words.append(wor)


clean_words = sorted(set(clean_words))


with open("words2.txt", 'w') as f:

    f.write(clean_words[0])
    for w in clean_words[1:]:
        f.write("\n" + w)