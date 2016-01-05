import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import { Grid } from 'react-bootstrap';
import { Alert } from 'react-bootstrap';
import { Glyphicon } from 'react-bootstrap';
import { Label } from 'react-bootstrap';

export default class HomePage extends React.Component {
  render() {
    return (
      <div>
        <NavMain activePage="home" />

        <PageHeader
            title="Black Crystal"
            subTitle="..." />

        <PageFooter />
      </div>
    );
  }
}
