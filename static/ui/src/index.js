import React from 'react';
import { createHistory, createHashHistory } from 'history';
import Root from './Root';
import jQuery from 'jquery';
import bootstrap from 'bootstrap';
import ReactDOM from 'react-dom';

const rootEl = document.getElementById('root');
// Use hash location for Github Pages
// but switch to HTML5 history locally.
const history = process.env.NODE_ENV === 'production' ?
  createHashHistory() :
  createHistory();

ReactDOM.render(<Root history={history} />, rootEl);
