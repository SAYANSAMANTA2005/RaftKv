FOR CREATING MULTIPLE NODES-->

# ❌ Wrong (PowerShell splits -Dexec.mainClass from =value)
mvn exec:java -Dexec.mainClass="com.sayan.raftkv.Main" -Dexec.args="Config/node1.properties"

# ✅ Correct (quotes wrap the entire -D flag including key and value)
# Run Node1-->
mvn exec:java "-Dexec.mainClass=com.sayan.raftkv.Main" "-Dexec.args=Config/node1.properties"


 # Run Node2-->
 mvn exec:java "-Dexec.mainClass=com.sayan.raftkv.Main" "-Dexec.args=Config/node2.properties"

# Run Node3-->
mvn exec:java "-Dexec.mainClass=com.sayan.raftkv.Main" "-Dexec.args=Config/node3.properties"





ERROR WAS-->

Root Cause Summary

There were two separate problems, both now fixed:

#	Problem	Root Cause	Fix
1	Unknown lifecycle phase ".mainClass=..."	PowerShell quoting — wrapping only the value in quotes causes PowerShell to split -Dkey and =value into separate tokens, so Maven saw .mainClass=... as a lifecycle phase name	Move the quotes to wrap the entire -Dkey=value flag: "-Dexec.mainClass=com.sayan.raftkv.Main"
2	FileNotFoundException: Config\node1.properties	The config files lived inside src/main/java/com/.../Config/ but Maven's working directory is the project root. There was no Config/ folder at the root	Created Config/node1.properties, Config/node2.properties, and Config/node3.properties at the project root

Note: I also changed hostip from 192.168.0.100 to 127.0.0.1 (localhost) in the new root-level configs so nodes can communicate locally on your machine.