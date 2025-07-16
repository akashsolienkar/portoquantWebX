#Custom LFU Cache Stratergy

**Stratergy**
```
🟢 ON APPLICATION STARTUP

Goal: Load Redis cache with as many high-frequency tickers as possible from DB, up to the cache capacity.

✅ What to do:

Query top N tickers by frequency (accessFrequency) from DB.
Load their HistoricalPrice data (last 257) and cache them.
Set their Redis score based on accessFrequency.
If DB is empty → nothing gets cached (and Redis starts cold).

🔄 DURING RUNTIME

⚡️ On request for a ticker:
Step 1: Check if ticker is in Redis.
✅ Yes: Return data, increment Redis frequency.
❌ No:
  Check in DB for prices:
  ✅ If found:
    Check if cache is full:
      ✅ Full → Evict least-frequent from Redis , save its frequency to DB. and load the one from db with its frequency
        ❌ Not full → No need to evict. add to cache
        Load the new ticker into Redis.
        Restore frequency from DB if available.

  ❌ If not in DB:
    Fetch from external API
    Save to DB
    Load to Redis using above logic.

🔁 PERIODICALLY (Configurable via cron)

Batch sync Redis frequencies back to DB (to avoid losing usage stats).
convert them into ranks and load again into cache refresh
You can do this every few minutes/hours/day based on system usage.
```
**Workflow**

<img width="833" height="813" alt="image" src="https://github.com/user-attachments/assets/2e198c9b-b4ad-445f-ab0a-fc1f1253fdde" />


**Comparison with Standard LFU**
```
Feature	                     Your Custom Plan	                              Traditional LFU Cache

🔁 Persistent frequency count	✅ Stored in DB            	                  ❌ Only in memory
♻️ Warm start on app restart	✅ Preload top-N from DB	                    ❌ Cold start every time
⚙️ Cache frequency control   	✅ Configurable sync to DB	                  ❌ No concept of sync
💾 Eviction handling	        ✅ Evict LFU + persist frequency	            ✅ Evict LFU (no tracking)
💡 Domain-awareness         	✅ Based on Asset, Ticker, HistoricalPrice	  ❌ Generic key-value
🔍 Miss strategy              ✅ Fallback to DB → API → Save to DB + Redis	❌ No fallback (miss = null)
🧠 Intelligent eviction	      ✅ Compare frequency of new vs old	          ❌ Blind LFU count
```

**PROS**
Keeps your Redis cache high-quality.
Frequency continuity: Retains access patterns across app restarts.
 


