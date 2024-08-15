# ip4vscan_snmp_provision
:네트워크 내 호스트를 스캔하면서 세팅된 SNMP Agent를 찾는 프로그램

### 주요 기능
<ol>
<li>IP범위 내 호스트IP 확인(네트워크 및 CIDR 또는 시작IP와 끝IP)</li>
<li>IPv4 <-> int 변환</li>
<li>호스트 Ping 확인</li>
<li>호스트 SNMP Agent여부 확인</li>
<li>SNMP Agent 장비 중 관리중이지 않은 장비를 데이터베이스에 저장</li>
</ol>

# Specification
<ul>
  <li>java8</li>
  <li>Mysql 8.0.20</li>
</ul>

# Description

##### 호스트 체크 결과 (Ping 및 SNMP Agent 여부 확인)
![Untitled](https://github.com/user-attachments/assets/1af79f25-3219-4844-813e-c9665f0cbea0)



##### 블로그 정리
https://mabb.tistory.com/608
