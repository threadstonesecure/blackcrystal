import React from 'react';
import LinkedStateMixin from 'react-addons-linked-state-mixin';
import { Input, Tooltip, OverlayTrigger, Glyphicon, Grid, Row, Col } from 'react-bootstrap';

const ResourceTypeGit = React.createClass({
    mixins: [LinkedStateMixin],
    getInitialState: function () {
        return {repository: this.props.resource.repository,
            version: this.props.resource.version,
            remote: this.props.resource.remote,
            acceptHostkey: this.props.resource.acceptHostkey};
    },

    render() {
        const acceptHostKeyToolTip = (
            <Tooltip id={1}>if checked, adds the hostkey for the repo url if not already added. If ssh_opts contains "-o
                StrictHostKeyChecking=no", this parameter is ignored.</Tooltip>
        );
        return (
            <div>
                <Grid>
                    <Row>
                        <Input type="text" label="Repository"
                               valueLink={this.linkState('repository')}
                               placeholder="SSH, or HTTP protocol address of the git repository."/>
                    </Row>
                    <Row>
                        <Input type="text" label="Version"
                               valueLink={this.linkState('version')}
                               placeholder="This can be the full 40-character SHA-1 hash, the literal string HEAD, a branch name, or a tag name."/>
                    </Row>
                    <Row>
                        <Input type="text" label="Remote"
                               valueLink={this.linkState('remote')}
                               placeholder="Name of the remote"/>
                    </Row>
                    <Row>
                        <Col md={2}>

                            <Input type="checkbox" label="Accept Hostkey"
                                   checkedLink={this.linkState('acceptHostkey')}/>
                        </Col>
                        <Col>
                            <OverlayTrigger placement="left" overlay={acceptHostKeyToolTip}>
                                <Glyphicon glyph="info-sign"/>
                            </OverlayTrigger>
                        </Col>
                    </Row>

                </Grid>
            </div>
        );
    }
});

export default ResourceTypeGit;
