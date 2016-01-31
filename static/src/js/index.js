import React from 'react';
import { createHistory, createHashHistory } from 'history';
import Root from './Root';
import ReactDOM from 'react-dom';
import '../lib/jquery-2.2.0.min.js';
import '../css/style.css';
import '../css/AdminLTE.min.css';
import '../css/skins/_all-skins.min.css';
import '../css/ionicons.min.css';
import '../css/bootstrap.min.css';
import '../css/bootstrap-theme.min.css';


const rootEl = document.getElementById('root');
// Use hash location for Github Pages
// but switch to HTML5 history locally.
const history = process.env.NODE_ENV === 'production' ?
  createHashHistory() :
  createHistory();

ReactDOM.render(<Root history={history} />, rootEl);
