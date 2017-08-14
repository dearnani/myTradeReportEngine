"# myTradeReportEngine" 
 August 14 2017
TradedReportEngine facilitates a report with the following :
 - Amount in Currency settled Buy everyday - BUY SUMMARY
 - Amount in Currency settled Sell everyday - SELL SUMMARY
 - Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest
amount for a buy instruction, then foo is rank 1 for outgoing

Technical Brief:
Used Json to read and prase without using any thrid party library.
Printed the data at the Console.
Given JUnit Test class for high level operations for ReportEngine
Maven configuration to run the JUnit Test cases.

Assumption:

	- 'AgreedFx', 'Units' & 'Price Per Unit' are not null.
 	-  Provided output for days where a Buy/Sell was settled.
 	- 'Rankings of entities based on incoming and outgoing amount' provides a summary of the total incoming and outgoing entities (highest first).
 On Design:
 	
 
Desclaimer :

This repository was Created By Narasimha Rao Sunkara towards Technical Test given for a Captive.
If any additonal information required feel free to tweet me @_sunkara
