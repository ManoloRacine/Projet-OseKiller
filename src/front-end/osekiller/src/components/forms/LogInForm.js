import { useState } from "react";

const LogInForm = (props) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = () => {
    console.log("Logging in...");
  };

  return (
    <div
      className="d-flex flex-column justify-content-evenly align-items-center"
      style={{ minHeight: "90vh" }}
    >
      <h1 className="display-1">{props.title}</h1>
      <form
        className="d-flex justify-content-between align-items-center w-50 p-5 bg-primary text-white rounded"
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
              <button className="btn btn-info" disabled>
                Loading...
              </button>
            ) : (
              <button type="submit" className="btn btn-info">
                Se connecter
              </button>
            )}
          </div>
        </div>

        <div className="col-sm-5">
          <div className="my-3">
            <p>Vous n'avez pas de compte?</p>
            <button className="btn bg-info" onClick={props.changeForm}>
              S'inscrire
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default LogInForm;
