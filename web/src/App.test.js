import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';

it('renders without crashing', () => {
  window.fetch = () => Promise.resolve({ json: () => ({ data: { example: { message: 'Hello!' } } }) });

  const div = document.createElement('div');
  ReactDOM.render(<App />, div);
  ReactDOM.unmountComponentAtNode(div);
});
