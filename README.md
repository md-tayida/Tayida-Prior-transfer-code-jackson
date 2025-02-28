# Tayida-Prior-transfer-code-jackson

## 📌 Assignment (โจทย์ 2024 FE _ BE)

โปรแกรมสำหรับแปลงข้อมูลจาก **Node-based UI** ให้อยู่ในรูปแบบ **Array Index-based** โดยดึงข้อมูลจาก  
[📄 rawdata.txt](https://storage.googleapis.com/maoz-event/rawdata.txt)  

### **Tools & Libraries **  
- **Maven** ในการจัดการ dependencies  
- **Jackson** สำหรับจัดการข้อมูล JSON
- **Java version**: java 21.0.6 

---

## 🔹 ตัวอย่าง output แบบ Array Index-based
```output
Nodes = ['input', 'org.maoz.prehandle.workers.neoai.aiclient.embedding.VoyageVerticle', 'org.maoz.prehandle.workers.neoai.httpclient.HttpClientAdapterVerticle', 'org.maoz.prehandle.workers.neoai.aiclient.embedding.util.VoyageTransformVerticle', 'org.maoz.prehandle.workers.neoai.ebtransform.ToPublishVerticle ', 'org.maoz.prehandle.workers.neoai.notify.LineVerticle', 'org.maoz.prehandle.workers.neoai.notify.FacebookVerticle', 'org.maoz.prehandle.workers.neoai.notify.DiscordVerticle', 'org.maoz.prehandle.workers.neoai.output.OutputVerticle']
addressIn = ['', voyage-embed-node-2, http-client-adapter-verticle-node-4, voyage-transform-node-3, to-publish-verticle-node-10, line-node-7, facebook-node-8, discord-node-9, output-node-10]
addressOut = ['voyage-embed-node-2', 'http-client-adapter-verticle-node-4', 'voyage-transform-node-3', 'to-publish-verticle-node-10', '[line-node-7, facebook-node-8, discord-node-9, output-node-10]', '', '', '', '']
```

### คำอธิบาย Output

Nodes: เก็บ type ของ Node (เรียงตามเส้นเชื่อมออกจาก Node เริ่มต้น)

addressIn: เก็บเส้นเชื่อมที่เข้าหา Node ที่ตำแหน่ง index เดียวกันกับ Node ('' หมายถึงไม่มีเส้นเข้า)

addressOut: เก็บเส้นเชื่อมที่ออกจาก Node ตำแหน่ง index เดียวกันกับ Node ('' หมายถึงไม่มีเส้นออก)
