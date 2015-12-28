import React from 'react';
import {IndexRoute, Route} from 'react-router';

import HomePage from './../HomePage';
import Jobs from './../jobs/Jobs';
import Resources from './../resources/Resources';
import Resource from './../resources/Resource';
import JobDetails from './../jobs/JobDetails';
import NotFoundPage from './../NotFoundPage';
import Root from './../../Root';

export default (
  <Route path="/" component={Root}>
    <IndexRoute component={HomePage} />
    <Route path="jobs" component={Jobs} />
    <Route path="resources" component={Resources} />
    <Route path="new/resource" component={Resource} />
    <Route path="job/:name" component={JobDetails} />
    <Route path="*" component={NotFoundPage} />
  </Route>
);
