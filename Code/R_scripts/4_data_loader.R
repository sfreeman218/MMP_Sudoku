repairgen <- c(15,10,7,10,9,9,9,12,7,13)
multgen <- c(76,48,21,38,28,31,32,41,18,57)
repairtime <- c(14,8,5,7,6,6,6,7,4,10)
multtime <- c(79,44,20,35,25,29,29,38,16,55)


ninerepairgen <- c(6574,667,13536,19403,2177,9718,3618,6701,4539,29866)
ninemultgen <- c(7097,4814,20942,22558,6353,6431,8205,21168,5111,5848)
ninerepairtime <- c(17538,1750,34280,50014,5646,24910,9489,17315,11617,81528)
ninemulttime <- c(22796,15504,65925,74607,21223,21826,27119,69251,16861,18579)

ninerepairgenm <- c(40187,

ninemulttimeM <- c(36265,13381,13555,13634,15076,14482,13586,11372,14244,14322)
ninerepairtimeM <- c(14261,754,7160,13396,14878,12661,9470,12534,13133,13278)


my_data <- data.frame(group = rep(c("Hybrid", "MultObj"), each = 10),weight = c(repairgen, multgen))
print(my_data)



res <- wilcox.test(weight ~ group, data = my_data, exact = FALSE)
res

fouronerep <- c(13,18,11,12,12,16,16,15,15,13,14,13,16,13,12,15,14,19,14,15,17,14,21,14,13,13,14,14,16,15)
fouronemult <- c(85,72,38,61,72,96,73,28,87,84,89,101,97,94,85,102,66,46,78,87,105,56,79,34,95,97,66,68,72,95)

my_data <- data.frame(group = rep(c("Hybrid", "MultObj"), each = 10),weight = c(ninerepairgen, ninemultgen))