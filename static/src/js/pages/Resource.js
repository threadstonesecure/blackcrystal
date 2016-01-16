import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import ResourceType from '../components/resources/ResourceType';
import LinkedStateMixin from 'react-addons-linked-state-mixin';
import { resourceURI, resourceDetailURI } from '../utils/Config'
import {Button, Alert,Grid,Row,Col,Navbar,Nav,NavDropdown,MenuItem,NavItem, Glyphicon,Input} from 'react-bootstrap';
import $ from 'jquery';


const Resource = React.createClass({
  mixins: [LinkedStateMixin],
  save(event){
    var resource = this.refs.resourceType.collect()
    resource.name = this.state.name;
    this.put(resource);
  },
  put(data) {
    var request = $.ajax({
      type: "PUT",
      url: resourceURI(),
      contentType: 'application/json',
      data: JSON.stringify(data),
      dataType: "json"
    });

    request.done(function (msg) {
      //TODO use react-router transitionTo
      $(location).attr('href', '../resources')
    });

    request.fail(function (jqXHR, msg) {
      console.log("Request failed: " + msg);
    });

  },
  loadData() {
    return $.getJSON(resourceDetailURI(this.props.params.name));
  },
  componentDidMount(){
    //If name is not null, it is deducted that is in edit mode
    //If is null, new resource is to be added
    //TODO needs better way to decide whether it is edit or add
    if (this.props.params.name != null) {
      this.loadData().success(function (data) {
        if (this.isMounted()) {
          console.log(data);
          this.setState(data);
          //this.refs.resourceSelector.setState({selected: this.state.resourceName});
        }
      }.bind(this))
    }
  },
  getInitialState() {
    return {name: '', type: '', directoryPath: '', acceptHostkey: false};
  },
  render() {
    return (
      <div>
        <NavMain activePage="resources"/>
        <Grid>
          <Row>

            <div className="box box-success">
              <div className="box-header with-border">
                <h3
                  className="box-title">{this.props.params.name != null ? this.props.params.name : "New Resource"}</h3>
              </div>

              <form role="form">
                <div className="box-body">

                  <div className="form-group">
                    <label>Resource Name</label>
                    <input type="text" className="form-control" valueLink={this.linkState('name')}
                           placeholder="Resource Name"></input>
                  </div>


                  <div className="form-group">
                    <ResourceType ref="resourceType" selected={this.state.type} resource={this.state}/>
                  </div>


                  <div className="box-footer">
                    <button type="submit" className="btn btn-primary" onClick={this.save}>Save</button>
                  </div>

                </div>
              </form>

            </div>
          </Row>
        </Grid>
        <PageFooter />
      </div>
    );
  }
});

export default Resource;
