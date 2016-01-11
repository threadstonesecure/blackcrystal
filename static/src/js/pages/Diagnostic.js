import React from 'react';
import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import { Row, Col, Grid  } from 'react-bootstrap';

const Overview = React.createClass({
  getInitialState() {
    return {};
  },
  componentDidMount(){
  },
  render() {
    return (
      <div>
        <NavMain activePage="diagnostic"/>
        <PageHeader title="Diagnostic"/>
        <Grid>
          <Row>
            <div className="box box-success">
              <div className="box-header with-border">
                <h3 className="box-title"></h3>
              </div>
              <div className="box-body">
                To be implemented....
              </div>
            </div>
          </Row>
        </Grid>
        <PageFooter />
      </div>
    );
  }
});

export default Overview;
