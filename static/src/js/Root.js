import React, { PropTypes, Component } from 'react';
import { Router, Route } from 'react-router';

import App from './App';
import Jobs from './pages/Jobs';
import Overview from './pages/Overview';
import Resources from './pages/Resources';
import JobDetails from './pages/JobDetails';
import HomePage from './pages/HomePage';
import Resource from './pages/Resource';
import Execution from './pages/Execution';
import Executions from './pages/Executions';
import NotFoundPage from './pages/NotFoundPage';


export default class Root extends Component {
  static propTypes = {
    history: PropTypes.object.isRequired
  }

  render() {
    const { history } = this.props;
    return (
      <Router history={history}>
        <Route  path="/" component={App}>
            <Route path="jobs" component={Jobs} />
            <Route path="resources" component={Resources} />
            <Route path="new/resource" component={Resource} />
            <Route path="job/:name" component={JobDetails} />
            <Route path="job/:name/executions" component={Executions} />
            <Route path="job/:name/execution/:id" component={Execution} />
            <Route path="new/job" component={JobDetails} />
            <Route path="overview" component={Overview} />
            <Route path="*" component={NotFoundPage} />
          </Route>
      </Router>
    );
  }
}
