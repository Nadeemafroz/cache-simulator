import { useState } from "react";

function App() {
  const [key, setKey] = useState("");
  const [value, setValue] = useState("");
  const [response, setResponse] = useState("");
  const [strategy, setStrategy] = useState("LRU");
  const [keys, setKeys] = useState([]);

  const BASE_URL = "http://localhost:8080/cache";

  // 🔹 Fetch cache keys
  const fetchKeys = async () => {
    const res = await fetch(`${BASE_URL}/keys`);
    const data = await res.json();
    setKeys(data);
  };

  // 🔹 Set strategy
  const setCacheStrategy = async () => {
    const res = await fetch(
      `${BASE_URL}/strategy?type=${strategy}&capacity=3`,
      { method: "POST" }
    );
    const text = await res.text();
    setResponse(text);
    fetchKeys();
  };

  // 🔹 Put value
  const putValue = async () => {
    const res = await fetch(
      `${BASE_URL}/put?key=${key}&value=${value}`,
      { method: "POST" }
    );
    const text = await res.text();
    setResponse(text);
    fetchKeys();
  };

  // 🔹 Get value
  const getValue = async () => {
    const res = await fetch(`${BASE_URL}/get?key=${key}`);
    const text = await res.text();
    setResponse(text);
    fetchKeys();
  };

  // 🔹 Metrics
  const getMetrics = async () => {
    const res = await fetch(`${BASE_URL}/metrics`);
    const text = await res.text();
    setResponse(text);
  };

  return (
    <div style={{ padding: "20px", fontFamily: "Arial" }}>
      <h2>Cache Simulator</h2>

      {/* Strategy */}
      <div>
        <select value={strategy} onChange={(e) => setStrategy(e.target.value)}>
          <option value="LRU">LRU</option>
          <option value="LFU">LFU</option>
        </select>
        <button onClick={setCacheStrategy} style={{ marginLeft: "10px" }}>
          Set Strategy
        </button>
      </div>

      <br />

      {/* Inputs */}
      <input
        placeholder="Key"
        value={key}
        onChange={(e) => setKey(e.target.value)}
        style={{ marginRight: "10px" }}
      />
      <input
        placeholder="Value"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      />

      <br /><br />

      {/* Actions */}
      <button onClick={putValue}>Put</button>
      <button onClick={getValue} style={{ marginLeft: "10px" }}>
        Get
      </button>
      <button onClick={getMetrics} style={{ marginLeft: "10px" }}>
        Metrics
      </button>

      {/* Response */}
      <h3>Response:</h3>
      <p>{response}</p>

      {/* Cache Visualization */}
      <h3>Cache State:</h3>
      <p>← Most Recent | Least Recent →</p>

      <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
        {keys.length === 0 ? (
          <p>No items in cache</p>
        ) : (
          keys.map((k, index) => (
            <div
              key={index}
              style={{
                padding: "15px",
                border: "2px solid black",
                borderRadius: "8px",
                minWidth: "40px",
                textAlign: "center",
                fontWeight: "bold",
                backgroundColor: "#f0f0f0"
              }}
            >
              {k}
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default App;