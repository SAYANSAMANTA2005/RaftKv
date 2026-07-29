All 3 config files are ready. Open 3 separate PowerShell terminals in the project folder and run one command in each:

Terminal 1 — Node 1 (port 5009)

powershell
cd "e:\Program Files\eclipse-workspace\raftkv"
mvn exec:java "-Dexec.mainClass=com.sayan.raftkv.Main" "-Dexec.args=Config/node1.properties"
Terminal 2 — Node 2 (port 5002)

powershell
cd "e:\Program Files\eclipse-workspace\raftkv"
mvn exec:java "-Dexec.mainClass=com.sayan.raftkv.Main" "-Dexec.args=Config/node2.properties"
Terminal 3 — Node 3 (port 5003)

powershell
cd "e:\Program Files\eclipse-workspace\raftkv"
mvn exec:java "-Dexec.mainClass=com.sayan.raftkv.Main" "-Dexec.args=Config/node3.properties"
Start order doesn't matter — each node will say Peer X is offline, skipping for any node that isn't up yet, and that's fine. Once all 3 are running, they'll accept connections from each other.

Note: Node 1's port is 5009 in node1.properties but node.1.port=5009 in cluster.properties. Make sure both match if you change it back to 5001