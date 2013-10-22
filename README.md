LevelDBUI
=========

A simple ui for levelDb(java).when i join the project [dain/LevelDB](https://github.com/dain/leveldb/),
i find that it is necessary to write a program for managing the LevelDB.Under the inspiration  of mx4j httpAdapter,i 
write a same program  for levelDB.

# Current status

Currently the code base is basically functional,but i think  there are many things needed to do. 


## API Usage:

  Options options = new Options();
  options.maxOpenFiles(100);
  options.createIfMissing(true);
  File databaseDir = new File("D:/temp/levelDb");

  LevelDbUIMain.start(options, databaseDir);



