import React from 'react';
import {IndexRoute, Route} from 'react-router';

import HomePage from './../HomePage';
import Jobs from './../jobs/Jobs';
import Resources from './../resources/Resources';
import Resource from './../resources/Resource';
import Overview from './../overview/Overview';
import JobDetails from './../jobs/JobDetails';
import Execution from './../executions/Execution';
import Executions from './../executions/Executions';
import NotFoundPage from './../NotFoundPage';
import Root from './../../Root';

export default (
  <Route path="/" component={Root}>
    <IndexRoute component={HomePage} />
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
);
