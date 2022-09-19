import { useState } from "react";

const LogInForm = (props) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsLoading(true);
    fetch("http://localhost:8000/users")
      .then((res) => {
        if (!res.ok) {
          throw Error("could not fetch the data for that resource");
        }
        res.json();
      })
      .then((json) => {
        console.log(json);
        setIsLoading(false);
      })
      .catch((err) => {
        if (err.name !== "AbortError") {
          console.log("Erreur:" + err);
          setIsLoading(false);
        }
      });
  };

  return (
    <div
      className="d-flex flex-column justify-content-evenly align-items-center"
      style={{ minHeight: "90vh" }}
    >
      <h1 className="display-1">{props.title}</h1>
      <form
        className="d-flex justify-content-between align-items-center w-50 p-5 text-white rounded"
        style={{backgroundColor : "#2C324C"}}
        onSubmit={handleSubmit}
      >
        <div className="col-sm-5">
          <div className="mb-3">
            <label className="form-label" htmlFor="username">
              Courriel
            </label>
            <input
              className="form-control"
              type="email"
              id="email"
              value={email}
              onChange={({ target }) => setEmail(target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label" htmlFor="password">
              Mot de passe
            </label>
            <input
              className="form-control"
              id="password"
              type="password"
              value={password}
              onChange={({ target }) => setPassword(target.value)}
              required
            />
          </div>

          <div className="mb-3">
            {isLoading ? (
              <button className="btn" style={{backgroundColor : "#ee7600"}} disabled>
                <div className="spinner-border" role="status">
                  <span className="visually-hidden">Loading...</span>
                </div>
              </button>
            ) : (
              <button type="submit" className="btn" style={{backgroundColor : "#ee7600"}}>
                Se connecter
              </button>
            )}
          </div>
        </div>

        <div className="col-sm-5">
          <div className="my-3">
            <p>Vous n'avez pas de compte?</p>
            <button className="btn" style={{backgroundColor : "#ee7600"}} onClick={props.changeForm}>
              S'inscrire
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default LogInForm;
