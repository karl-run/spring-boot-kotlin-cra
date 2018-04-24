import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  state = {
    message: null,
  };

  componentDidMount() {
    fetch('/api/hello')
      .then(response => response.json())
      .then(response => { this.setState({ message: response.message })});
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
        <p>
          Message from backend: {this.state.message}
        </p>
      </div>
    );
  }
}

export default App;
