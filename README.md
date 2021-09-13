## Automatic generator of semi-structured documents (SSDs)
This project generates semi-structured documents ( invoices, payslips, and receipts). 

This is the Java implementation of our two papers :

Blanchard, J., Belaïd, Y., & Belaïd, A. (2019, September). Automatic generation of a custom corpora for invoice analysis and recognition. In 2019 International Conference on Document Analysis and Recognition Workshops (ICDARW). IEEE.

and
 
Belhadj, D., Belaïd, Y., & Belaïd, A. (2021, September). Automatic Generation of Semi-structured Documents. In International Conference on Document Analysis and Recognition (pp. 191-205). Springer, Cham.

### SSDs generation  : 
- We can generate locally the three SSDs as well as the cloned invoices separately by launching :
 
  Testlanuch
 
- We can choose to generate these documents via the API web interface by launching :
mvn quarkus:dev 
and then by accessing :
http://localhost:9080/api/ws/ 

### Diversity evaluation :
- We can evaluate the diversity of the local generated SSD datasets using 4 metrics :
Alignement, overlapping, SCR_score, and SELF-BLEU :
    TestDiversityLaunch


#### Annotations in Invoices GEDI File : 

   * Invoice Number - IN
   * Seller(Company) name - SN
   * Seller Address - SA
   * Seller Vat Number/TVA- SVAT
   * Seller Identifier Number(Siren) - SCID
   * Seller Siret - SSIRET
   * Seller TOA/APE - STOA
   * Seller RCS - SRCS
    Seller Website - SWEB
    Seller Email - SEMAIL
   * Seller Contact Number - SCN
   * Seller Fax Number - SFAX
    E-commerce Platform Name(Like Amazon, Ebay,..) - EN
    E-commerce Platform Website - EWEB
    E-commerce Order Reference - EOID
    * Invoice Date - IDATE
    Tax Point Date (date of supply) - TPDATE
    * Billing Name - BN
    * Billing Address - BA
    Billing Contact Number - BCN
    * Shipping Name - SHN
    * Shipping Address - SHA
    Shipping Contact Number -SHCN
    * Table (and its content) -TBL
    * Client Number - CNUM
    * Order Number - ONUM
    * Payment Mode - PMODE
    * Rest words - undefined
