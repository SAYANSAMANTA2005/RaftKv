    open terminal1 -->start the Reciver server

mvn compile exec:java "-Dexec.mainClass=com.sayan.raftkv.network.Reciever"


  open terminal2 -->start the Client(main)

mvn compile exec:java "-Dexec.mainClass=com.sayan.raftkv.Main"
